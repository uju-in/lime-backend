package com.programmers.lime.domains.item.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.model.FavoriteInfoForItemSummary;
import com.programmers.lime.domains.item.model.ItemCursorIdInfo;
import com.programmers.lime.domains.item.model.ItemCursorSummary;
import com.programmers.lime.domains.item.model.ItemInfoForItemSummary;
import com.programmers.lime.domains.item.model.ItemSortCondition;
import com.programmers.lime.domains.item.model.ItemSummary;
import com.programmers.lime.domains.item.model.ReviewInfoForItemSummary;
import com.programmers.lime.domains.item.repository.ItemRepository;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemCursorReader {

	private static final int DEFAULT_SIZE = 20;
	private final ItemRepository itemRepository;

	public CursorSummary<ItemCursorSummary> readByCursor(
		final String keyword,
		final CursorPageParameters parameters,
		final ItemSortCondition itemSortCondition,
		final Hobby hobby
	) {
		String trimmedKeyword = keyword.trim();

		// 비어 있는 키워드일 경우 빈 리스트 반환
		if (trimmedKeyword.isEmpty()) {
			return new CursorSummary<>(
				null,
				0,
				Collections.emptyList()
			);
		}

		// 키워드에 해당하는 아이템 아이디와 커서 아이디를 가져옴
		List<ItemCursorIdInfo> itemCursorIdInfos = itemRepository.getItemIdsByCursor(
			trimmedKeyword,
			parameters.cursorId(),
			getPageSize(parameters),
			itemSortCondition,
			hobby
		);

		// 키워드에 해당하는 아이템 아이디와 커서 아이디를 이용하여 커서 아아이디를 포함하는 아이템 정보를 리시트로 가져옴
		List<ItemCursorSummary> itemCursorSummaries = getItemCursorSummaries(itemCursorIdInfos);

		// itemCursorSummaries는 아이템에 대한 정보와 아이템에 해당하는 커서 아이디를 가지고 있는 리스트
		// 반환되는 CursorSummary<ItemCursorSummary> 객체는 다음 커서 아이디, 현재 페이지 아이템 개수, 요청한 사이즈 만큼의 아이템 정보를 가지고 있는 객체
		return CursorUtils.getCursorSummaries(itemCursorSummaries);
	}

	private List<ItemCursorSummary> getItemCursorSummaries(final List<ItemCursorIdInfo> itemCursorIdInfos) {

		// 아이템 아이디 리스트를 가져옴
		List<Long> itemIds = itemCursorIdInfos.stream()
			.map(ItemCursorIdInfo::itemId)
			.toList();

		// 아이템 아이디를 이용하여 아이템 정보를 가져옴
		List<ItemSummary> itemSummaries = getItemSummaries(itemIds);

		// 커서 아이디를 포함 시켜서 아이템 정보를 저장한 ItemCursorSummary 리스트를 생성
		List<ItemCursorSummary> itemCursorSummaries = new ArrayList<>();
		for(int i = 0; i< itemCursorIdInfos.size(); i++) {
			ItemCursorIdInfo itemCursorIdInfo = itemCursorIdInfos.get(i);
			ItemSummary itemSummary = itemSummaries.get(i);

			ItemCursorSummary itemCursorSummary = new ItemCursorSummary(
				itemCursorIdInfo.cursorId(),
				itemSummary
			);

			itemCursorSummaries.add(itemCursorSummary);
		}

		return itemCursorSummaries;
	}

	private List<ItemSummary> getItemSummaries(final List<Long> itemIds) {

		Map<Long, ItemInfoForItemSummary> itemInfoMap  = itemRepository.getItemInfosByItemIds(itemIds)
			.stream()
			.collect(Collectors.toMap(ItemInfoForItemSummary::id, Function.identity()));

		Map<Long, ReviewInfoForItemSummary> reviewInfoMap  = itemRepository.getReviewInfosByItemIds(itemIds)
			.stream()
			.collect(Collectors.toMap(ReviewInfoForItemSummary::itemId, Function.identity()));

		Map<Long, FavoriteInfoForItemSummary> favoriteInfoMap  = itemRepository.getFavoriteInfosByItemIds(itemIds)
			.stream()
			.collect(Collectors.toMap(FavoriteInfoForItemSummary::itemId, Function.identity()));


		return itemIds.stream().map(itemId -> {

			if (!itemInfoMap.containsKey(itemId) ||
				!reviewInfoMap.containsKey(itemId) ||
				!favoriteInfoMap.containsKey(itemId)
			) {
				throw new BusinessException(ErrorCode.NOT_FOUND_WHILE_READING_ITEM_SUMMARY);
			}

			ItemInfoForItemSummary itemInfo = itemInfoMap.get(itemId);
			ReviewInfoForItemSummary reviewInfo = reviewInfoMap.get(itemId);
			FavoriteInfoForItemSummary favoriteInfo = favoriteInfoMap.get(itemId);

			return ItemSummary.builder()
				.id(itemInfo.id())
				.name(itemInfo.name())
				.price(itemInfo.price())
				.image(itemInfo.image())
				.reviewCount(reviewInfo.reviewCount())
				.favoriteCount(favoriteInfo.favoriteCount())
				.build();
		}).toList();
	}

	private int getPageSize(final CursorPageParameters parameters) {
		Integer parameterSize = parameters.size();

		if (parameterSize == null) {
			return DEFAULT_SIZE;
		}

		return parameterSize;
	}
}
