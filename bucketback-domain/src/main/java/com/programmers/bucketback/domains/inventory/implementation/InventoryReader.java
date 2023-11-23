package com.programmers.bucketback.domains.inventory.implementation;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.cursor.CursorUtils;
import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
import com.programmers.bucketback.domains.inventory.domain.InventoryItem;
import com.programmers.bucketback.domains.inventory.model.InventoryGetServiceResponse;
import com.programmers.bucketback.domains.inventory.model.InventoryInfoSummary;
import com.programmers.bucketback.domains.inventory.model.InventoryItemGetResponse;
import com.programmers.bucketback.domains.inventory.model.InventoryProfile;
import com.programmers.bucketback.domains.inventory.model.InventoryReviewItemSummary;
import com.programmers.bucketback.domains.inventory.repository.InventoryItemRepository;
import com.programmers.bucketback.domains.inventory.repository.InventoryRepository;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.implementation.ItemReader;
import com.programmers.bucketback.domains.item.model.ItemInfo;
import com.programmers.bucketback.domains.review.domain.Review;
import com.programmers.bucketback.domains.review.implementation.ReviewReader;
import com.programmers.bucketback.error.EntityNotFoundException;
import com.programmers.bucketback.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryReader {

	private static final int ITEM_IMAGE_LIMIT = 4;
	private static final int INVENTORY_PROFILE_LIMIT = 3;
	private static final int INVENTORY_ITEM_IMAGE_LIMIT = 3;
	private static final int DEFAULT_PAGING_SIZE = 20;

	private final InventoryRepository inventoryRepository;
	private final InventoryItemRepository inventoryItemRepository;
	private final ReviewReader reviewReader;
	private final ItemReader itemReader;

	public boolean isCreated(
		final Hobby hobby,
		final Long memberId
	) {
		return inventoryRepository.existsByHobbyAndMemberId(hobby, memberId);
	}

	/** 인벤토리 조회 */
	public Inventory read(
		final Long inventoryId,
		final Long memberId
	) {
		return inventoryRepository.findByIdAndMemberId(inventoryId, memberId)
			.orElseThrow(() -> {
				throw new EntityNotFoundException(ErrorCode.INVENTORY_NOT_FOUND);
			});
	}

	public Inventory read(final Long inventoryId) {
		return inventoryRepository.findById(inventoryId)
			.orElseThrow(() -> {
				throw new EntityNotFoundException(ErrorCode.INVENTORY_NOT_FOUND);
			});
	}

	/** 인벤토리 아이템 조회 */
	public List<InventoryItem> inventoryItemRead(final Long inventoryId) {
		return inventoryItemRepository.findByInventoryId(inventoryId);
	}

	/**
	 * 인벤토링 아이템 상세 조회
	 */
	public InventoryGetServiceResponse readDetail(final Long inventoryId) {
		Inventory inventory = read(inventoryId);

		List<InventoryItemGetResponse> inventoryItemGetResponses = inventory.getInventoryItems().stream()
			.map(inventoryItem -> itemReader.read(inventoryItem.getItem().getId()))
			.map(item -> InventoryItemGetResponse.of(ItemInfo.from(item), item.getUrl()))
			.toList();

		return InventoryGetServiceResponse.of(inventory, inventoryItemGetResponses);
	}

	/** 인벤토리 목록 조회 */
	public List<InventoryInfoSummary> readSummary(final Long memberId) {
		List<InventoryInfoSummary> results = inventoryRepository.findInfoSummaries(memberId);

		results.forEach(result ->
			result.setItemImages(
				result.getItemImages().stream()
					.limit(INVENTORY_ITEM_IMAGE_LIMIT)
					.collect(Collectors.toList())
			)
		);

		return results;
	}

	/** 내가 리뷰한 아이템 목록 조회 */
	public CursorSummary<InventoryReviewItemSummary> readReviewedItem(
		final Long memberId,
		final Long inventoryId,
		final Hobby hobby,
		final CursorPageParameters parameters
	) {
		int pageSize = getPageSize(parameters);

		List<Long> itemIdsFromInventory = null;

		if (inventoryId != null) {
			itemIdsFromInventory = read(inventoryId, memberId)
				.getInventoryItems().stream()
				.map(inventoryItem -> inventoryItem.getItem().getId())
				.toList();
		}

		List<Review> reviews = reviewReader.readByMemberId(memberId);
		List<Long> itemIdsFromReview = reviews.stream()
			.map(Review::getItemId)
			.toList();

		List<InventoryReviewItemSummary> summaries = itemReader.readReviewedItem(
			itemIdsFromReview,
			itemIdsFromInventory,
			hobby,
			parameters.cursorId(),
			pageSize
		);

		return CursorUtils.getCursorSummaries(summaries);
	}

	/** 마이페이지를 위한 인벤토리 프로필 조회(3개) */
	public List<InventoryProfile> readInventoryProfile(final Long memberId) {
		List<Inventory> inventories = inventoryRepository.findByMemberId(memberId);

		return selectInventoryProfile(inventories);
	}

	private List<InventoryProfile> selectInventoryProfile(final List<Inventory> inventories) {
		List<Inventory> selectedInventories = inventories.stream()
			.sorted(Comparator.comparing(Inventory::getModifiedAt).reversed())
			.limit(INVENTORY_PROFILE_LIMIT)
			.toList();

		return selectedInventories.stream()
			.map(inventory -> {
				List<String> itemImages = extractInventoryItemImages(inventory);

				return InventoryProfile.of(inventory, itemImages);
			})
			.toList();
	}

	private List<String> extractInventoryItemImages(final Inventory inventory) {
		return inventory.getInventoryItems().stream()
			.sorted(Comparator.comparing(InventoryItem::getModifiedAt).reversed())
			.limit(ITEM_IMAGE_LIMIT)
			.map(InventoryItem::getItem)
			.map(Item::getImage)
			.toList();
	}

	private int getPageSize(final CursorPageParameters parameters) {
		int pageSize = parameters.size() == null ? DEFAULT_PAGING_SIZE : parameters.size();
		return pageSize;
	}
}
