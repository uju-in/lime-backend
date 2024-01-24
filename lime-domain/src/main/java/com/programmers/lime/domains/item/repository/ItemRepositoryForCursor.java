package com.programmers.lime.domains.item.repository;

import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.inventory.model.InventoryReviewItemSummary;
import com.programmers.lime.domains.item.model.FavoriteInfoForItemSummary;
import com.programmers.lime.domains.item.model.ItemCursorIdInfo;
import com.programmers.lime.domains.item.model.ItemInfoForItemSummary;
import com.programmers.lime.domains.item.model.ItemSortCondition;
import com.programmers.lime.domains.item.model.ReviewInfoForItemSummary;

public interface ItemRepositoryForCursor {
	List<ItemCursorIdInfo> getItemIdsByCursor(
		final String keyword,
		final String cursorId,
		final int pageSize,
		final ItemSortCondition itemSortCondition,
		final Hobby hobby
	);

	List<ItemInfoForItemSummary> getItemInfosByItemIds(List<Long> itemIds);

	List<ReviewInfoForItemSummary> getReviewInfosByItemIds(List<Long> itemIds);

	List<FavoriteInfoForItemSummary> getFavoriteInfosByItemIds(List<Long> itemIds);

	List<InventoryReviewItemSummary> findReviewedItemByCursor(
		final List<Long> itemIdsFromReview,
		final List<Long> itemIdsFromInventory,
		final Hobby hobby,
		final String cursorId,
		final int pageSize
	);
}
