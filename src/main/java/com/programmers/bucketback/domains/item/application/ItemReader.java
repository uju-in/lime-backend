package com.programmers.bucketback.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.inventory.application.InventoryReviewItemSummary;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.repository.ItemRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
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
}
