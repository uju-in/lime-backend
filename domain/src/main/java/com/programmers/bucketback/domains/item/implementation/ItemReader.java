package com.programmers.bucketback.domains.item.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.inventory.model.InventoryReviewItemSummary;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.repository.ItemRepository;
import com.programmers.bucketback.error.EntityNotFoundException;
import com.programmers.bucketback.error.ErrorCode;

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
		final String cursorId,
		final int pageSize
	) {
		return itemRepository.findReviewedItemByCursor(
			itemIdsFromReview,
			itemIdsFromInventory,
			cursorId,
			pageSize
		);
	}

	public boolean existsItemsByUrl(final String itemURL) {
		return itemRepository.existsItemsByUrl(itemURL);
	}
}
