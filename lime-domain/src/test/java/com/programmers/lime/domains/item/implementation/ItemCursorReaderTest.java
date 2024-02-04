package com.programmers.lime.domains.item.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorPageParametersBuilder;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.domains.item.model.FavoriteInfoForItemSummary;
import com.programmers.lime.domains.item.model.FavoriteInfoForItemSummaryBuilder;
import com.programmers.lime.domains.item.model.ItemCursorIdInfo;
import com.programmers.lime.domains.item.model.ItemCursorIdInfoBuilder;
import com.programmers.lime.domains.item.model.ItemCursorSummary;
import com.programmers.lime.domains.item.model.ItemCursorSummaryBuilder;
import com.programmers.lime.domains.item.model.ItemInfoForItemSummary;
import com.programmers.lime.domains.item.model.ItemInfoForItemSummaryBuilder;
import com.programmers.lime.domains.item.model.ReviewInfoForItemSummary;
import com.programmers.lime.domains.item.model.ReviewInfoForItemSummaryBuilder;
import com.programmers.lime.domains.item.repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
class ItemCursorReaderTest {

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private ItemCursorReader itemCursorReader;

	@Test
	@DisplayName("커서를 이용해 아이템을 조회한다.")
	public void readByCursor() {
		// given
		String keyword = " 농구 ";
		CursorPageParameters parameters = CursorPageParametersBuilder.build();
		List<ItemCursorIdInfo> itemCursorIdInfos = ItemCursorIdInfoBuilder.buildMany();
		List<Long> itemIds = itemCursorIdInfos.stream().map(ItemCursorIdInfo::itemId).toList();

		List<ItemCursorSummary> itemCursorSummaries = ItemCursorSummaryBuilder.buildMany(itemIds);
		CursorSummary<ItemCursorSummary> expectedCursorSummary = CursorUtils.getCursorSummaries(itemCursorSummaries);

		List<ItemInfoForItemSummary> itemInfos = ItemInfoForItemSummaryBuilder.buildMany(itemIds);
		List<FavoriteInfoForItemSummary> favoriteInfoForItemSummaries = FavoriteInfoForItemSummaryBuilder.buildMany(itemIds);
		List<ReviewInfoForItemSummary> reviewInfoForItemSummaries = ReviewInfoForItemSummaryBuilder.buildMany(itemIds);

		given(itemRepository.getItemIdsByCursor(
				keyword.trim(),
				parameters.cursorId(),
				parameters.size(),
				null,
				null
			)
		).willReturn(itemCursorIdInfos);

		given(itemRepository.getItemInfosByItemIds(itemIds)).willReturn(itemInfos);
		given(itemRepository.getFavoriteInfosByItemIds(itemIds)).willReturn(favoriteInfoForItemSummaries);
		given(itemRepository.getReviewInfosByItemIds(itemIds)).willReturn(reviewInfoForItemSummaries);

		// when
		CursorSummary<ItemCursorSummary> actualCursorSummary = itemCursorReader.readByCursor(
			keyword,
			parameters,
			null,
			null
		);

		//then
		assertThat(actualCursorSummary)
			.usingRecursiveComparison()
			.isEqualTo(expectedCursorSummary);
	}
}