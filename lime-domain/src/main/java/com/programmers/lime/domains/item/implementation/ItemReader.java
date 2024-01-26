package com.programmers.lime.domains.item.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.inventory.model.InventoryReviewItemSummary;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.repository.ItemRepository;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemReader {

	private final ItemRepository itemRepository;

	public Item read(final Long itemId) {
		return itemRepository.findById(itemId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.ITEM_NOT_FOUND));
	}

	public List<InventoryReviewItemSummary> readReviewedItem(
		final List<Long> itemIdsFromReview,
		final List<Long> itemIdsFromInventory,
		final Hobby hobby,
		final String cursorId,
		final int pageSize
	) {
		return itemRepository.findReviewedItemByCursor(
			itemIdsFromReview,
			itemIdsFromInventory,
			hobby,
			cursorId,
			pageSize
		);
	}

	public boolean existsItemsByUrl(final String itemURL) {
		return itemRepository.existsItemsByUrl(itemURL);
	}

	public int getItemTotalCountByKeyword(final String keyword, final Hobby hobby) {
		String trimmedKeyword = keyword.trim();

		if (trimmedKeyword.isEmpty()) {
			return 0;
		}

		return itemRepository.countItemByKeywordAndHobby(trimmedKeyword, hobby);
	}

	public boolean existsAll(final List<Long> itemIds) {
		return itemRepository.existsAllByIdIn(itemIds);
	}

	public List<Item> readAll(final List<Long> itemIds) {
		return itemRepository.findAllByIdIn(itemIds);

	}
}
