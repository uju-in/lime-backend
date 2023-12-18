package com.programmers.bucketback.domains.inventory.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.common.model.ItemIdRegistry;
import com.programmers.bucketback.common.model.ItemIdRegistryBuilder;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
import com.programmers.bucketback.domains.inventory.domain.InventoryBuilder;
import com.programmers.bucketback.domains.inventory.model.InventoryGetServiceResponse;
import com.programmers.bucketback.domains.inventory.model.InventoryItemGetResponse;
import com.programmers.bucketback.domains.inventory.repository.InventoryRepository;
import com.programmers.bucketback.domains.item.domain.ItemBuilder;
import com.programmers.bucketback.domains.item.implementation.ItemReader;
import com.programmers.bucketback.domains.item.model.ItemInfo;

@ExtendWith(MockitoExtension.class)
public class InventoryReaderTest {

	@Mock
	private InventoryRepository inventoryRepository;

	@Mock
	private InventoryAppender inventoryAppender;

	@Mock
	private ItemReader itemReader;

	@InjectMocks
	private InventoryReader inventoryReader;

	@Test
	@DisplayName("이벤토리 아이템을 상세조회한다.")
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
	@DisplayName("인벤토리 목록을 조회한다.")
	void getInventories() {
		//given
		// Inventory inventory = InventoryBuilder.build();
		//when

		//then

	}

	@Test
	@DisplayName("내가 리뷰한 아이템 목록을 조회한다.")
	void getReviewedItems() {
		//given

		//when

		//then

	}

	@Test
	@DisplayName("마이페이지를 위한 인벤토리 프로필을 조회한다.")
	void getInventoryProfile() {
		//given
		ItemIdRegistry itemIdRegistry = ItemIdRegistryBuilder.build();
		Inventory inventory1 = InventoryBuilder.build(Hobby.BASKETBALL, itemIdRegistry);
		Inventory inventory2 = InventoryBuilder.build(Hobby.BASEBALL, itemIdRegistry);
		Inventory inventory3 = InventoryBuilder.build(Hobby.SWIMMING, itemIdRegistry);

		//when

		//then

	}
}
