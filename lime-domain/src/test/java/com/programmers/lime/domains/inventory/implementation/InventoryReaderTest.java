package com.programmers.lime.domains.inventory.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorPageParametersBuilder;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.domains.inventory.domain.Inventory;
import com.programmers.lime.domains.inventory.domain.InventoryBuilder;
import com.programmers.lime.domains.inventory.model.InventoryGetServiceResponse;
import com.programmers.lime.domains.inventory.model.InventoryItemGetResponse;
import com.programmers.lime.domains.inventory.model.InventoryProfile;
import com.programmers.lime.domains.inventory.model.InventoryReviewItemSummary;
import com.programmers.lime.domains.inventory.repository.InventoryRepository;
import com.programmers.lime.domains.item.domain.ItemBuilder;
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.domains.item.model.ItemInfo;
import com.programmers.lime.domains.review.ReviewBuilder;
import com.programmers.lime.domains.review.implementation.ReviewReader;

@ExtendWith(MockitoExtension.class)
public class InventoryReaderTest {

	@Mock
	private InventoryRepository inventoryRepository;

	@Mock
	private ReviewReader reviewReader;

	@Mock
	private ItemReader itemReader;

	@InjectMocks
	private InventoryReader inventoryReader;

	@Test
	@DisplayName("인벤토리 아이템을 상세조회한다.")
	void getInventoryDetail() {
		//given
		Long inventoryId = 1L;

		Inventory inventory = InventoryBuilder.build();
		List<InventoryItemGetResponse> inventoryItemGetResponses = inventory.getInventoryItems().stream()
			.map(inventoryItem -> inventoryItem.getItem())
			.map(item -> InventoryItemGetResponse.of(ItemInfo.from(item), item.getUrl()))
			.toList();

		given(inventoryRepository.findById(inventoryId))
			.willReturn(Optional.of(inventory));
		given(itemReader.read(anyLong()))
			.willReturn(ItemBuilder.build());

		//when
		InventoryGetServiceResponse response = inventoryReader.readDetail(inventoryId);

		//then
		assertThat(response.hobby()).isEqualTo(inventory.getHobby());
		assertThat(response.itemCount()).isEqualTo(inventory.getInventoryItems().size());
		assertThat(response.inventoryItemInfos())
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(inventoryItemGetResponses);
	}

	@Test
	@DisplayName("내가 리뷰한 아이템 목록을 조회한다.")
	void getReviewedItems() {
		//given
		Long memberId = 1L;
		Inventory inventory = InventoryBuilder.build();
		List<Long> itemIds = inventory.getInventoryItems().stream()
			.map(inventoryItem -> inventoryItem.getItem().getId())
			.toList();
		List<InventoryReviewItemSummary> summaries = InventoryBuilder.buildInventoryReviewItemSummaries(itemIds);
		CursorPageParameters parameters = CursorPageParametersBuilder.build(); // 이후 생성된 model로 대체 예정

		given(inventoryRepository.findByIdAndMemberId(anyLong(), anyLong()))
			.willReturn(Optional.of(inventory));
		given(reviewReader.readByMemberId(anyLong()))
			.willReturn(ReviewBuilder.buildMany());
		given(itemReader.readReviewedItem(
			anyList(),
			anyList(),
			any(Hobby.class),
			anyString(),
			anyInt()
		)).willReturn(summaries);

		//when
		CursorSummary<InventoryReviewItemSummary> cursorSummary =
			inventoryReader.readReviewedItem(
				memberId,
				inventory.getId(),
				Hobby.BASKETBALL,
				parameters
			);

		//then
		assertThat(cursorSummary.summaries()).usingRecursiveComparison().isEqualTo(summaries);
	}

	@Test
	@DisplayName("마이페이지를 위한 인벤토리 프로필을 조회한다.")
	void getInventoryProfile() {
		//given
		Long memberId = 1L;
		Inventory inventory1 = InventoryBuilder.build(Hobby.BASEBALL, new ItemIdRegistry(Arrays.asList(1L, 2L, 3L)));
		InventoryBuilder.setModifiedDate(inventory1, LocalDateTime.of(2024, 1, 1, 1, 1, 1));
		InventoryBuilder.setModifiedDate(inventory1.getInventoryItems(), LocalDateTime.of(2024, 1, 1, 1, 1, 1));
		Inventory inventory2 = InventoryBuilder.build(Hobby.BASEBALL, new ItemIdRegistry(Arrays.asList(4L, 5L, 6L)));
		InventoryBuilder.setModifiedDate(inventory2, LocalDateTime.of(2023, 1, 1, 1, 1, 1));
		InventoryBuilder.setModifiedDate(inventory2.getInventoryItems(), LocalDateTime.of(2023, 1, 1, 1, 1, 1, 1));
		Inventory inventory3 = InventoryBuilder.build(Hobby.BASEBALL, new ItemIdRegistry(Arrays.asList(7L, 8L, 9L)));
		InventoryBuilder.setModifiedDate(inventory3, LocalDateTime.of(2022, 1, 1, 1, 1, 1));
		InventoryBuilder.setModifiedDate(inventory3.getInventoryItems(), LocalDateTime.of(2022, 1, 1, 1, 1, 1, 1));
		Inventory inventory4 = InventoryBuilder.build(Hobby.BASEBALL, new ItemIdRegistry(Arrays.asList(10L, 11L, 12L)));
		InventoryBuilder.setModifiedDate(inventory4, LocalDateTime.of(2021, 1, 1, 1, 1, 1));
		InventoryBuilder.setModifiedDate(inventory4.getInventoryItems(), LocalDateTime.of(2021, 1, 1, 1, 1, 1, 1));

		given(inventoryRepository.findByMemberId(memberId))
			.willReturn(Arrays.asList(inventory1, inventory2, inventory3, inventory4));

		//when
		List<InventoryProfile> inventoryProfiles = inventoryReader.readInventoryProfile(memberId);

		//then
		assertThat(inventoryProfiles.size()).isEqualTo(3);
		assertThat(inventoryProfiles.get(2).hobby()).isEqualTo("야구");
	}
}
