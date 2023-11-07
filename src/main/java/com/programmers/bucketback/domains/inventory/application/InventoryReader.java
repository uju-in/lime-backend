package com.programmers.bucketback.domains.inventory.application;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryInfoSummary;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
import com.programmers.bucketback.domains.inventory.domain.InventoryItem;
import com.programmers.bucketback.domains.inventory.repository.InventoryItemRepository;
import com.programmers.bucketback.domains.inventory.repository.InventoryRepository;
import com.programmers.bucketback.domains.member.application.MemberReader;
import com.programmers.bucketback.domains.review.application.ReviewReader;
import com.programmers.bucketback.domains.review.domain.Review;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryReader {

	private final InventoryRepository inventoryRepository;
	private final InventoryItemRepository inventoryItemRepository;
	private final MemberReader memberReader;
	private final ReviewReader reviewReader;

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

	/** 인벤토링 아이템 조회 */
	public List<InventoryItem> inventoryItemRead(final Long inventoryId) {
		return inventoryItemRepository.findByInventoryId(inventoryId);
	}

	/** 인벤토리 목록 조회 */
	public List<InventoryInfoSummary> readSummary(final String nickname) {
		Long memberId = memberReader.readByNickname(nickname).getId();
		List<InventoryInfoSummary> results = inventoryRepository.findInfoSummaries(memberId);

		for (InventoryInfoSummary result : results) {
			result.setItemImages(
				result.getItemImages()
					.subList(0, Math.min(3, result.getItemImages().size()))
			);
		}

		return results;
	}

	/** 내가 리뷰한 아이템 목록 조회 */
	public InventoryGetReviewedItemResponse readReviewedItem(
		final Long inventoryId,
		final CursorPageParameters parameters
	) {
		int pageSize = parameters.size() == 0 ? 20 : parameters.size();

		Long memberId = MemberUtils.getCurrentMemberId();
		List<Long> itemIdsInInventory = read(inventoryId, memberId)
			.getInventoryItems().stream()
			.map(inventoryItem -> inventoryItem.getItem().getId())
			.toList();

		List<Review> reviews = reviewReader.readByMemberId(memberId);
		List<Long> reviewedItemIds = reviews.stream()
			.map(review -> review.getItemId())
			.toList();

		List<InventoryReviewedItem> inventoryReviewedItems = inventoryRepository.findReviewedItems(
			reviewedItemIds,
			itemIdsInInventory,
			parameters.cursorId(),
			pageSize
		);

		List<String> cursorIds = inventoryReviewedItems.stream()
			.map(inventoryReviewedItem -> generateReviewedItemCursorId(inventoryReviewedItem))
			.toList();

		String nextCursorId = cursorIds.size() == 0 ? null : cursorIds.get(cursorIds.size() - 1);

		List<InventoryReviewedItemCursorSummary> inventoryReviewedItemCursorSummaries =
			getInventoryReviewedItemCursorSummaries(inventoryReviewedItems, cursorIds);
		int itemCount = inventoryReviewedItemCursorSummaries.size();

		return new InventoryGetReviewedItemResponse(nextCursorId, itemCount, inventoryReviewedItemCursorSummaries);
	}

	private List<InventoryReviewedItemCursorSummary> getInventoryReviewedItemCursorSummaries(
		final List<InventoryReviewedItem> inventoryReviewedItems,
		final List<String> cursorIds
	) {
		List<InventoryReviewedItemCursorSummary> inventoryReviewedItemCursorSummaries =
			IntStream.range(0, inventoryReviewedItems.size())
				.mapToObj(i -> {
					String cursorId = cursorIds.get(i);
					InventoryReviewedItem inventoryReviewedItem = inventoryReviewedItems.get(i);

					return InventoryReviewedItemCursorSummary.of(cursorId, inventoryReviewedItem);
				}).toList();

		return inventoryReviewedItemCursorSummaries;
	}

	private String generateReviewedItemCursorId(final InventoryReviewedItem inventoryReviewedItem) {
		return inventoryReviewedItem.getCreatedAt().toString()
			.replace("-", "")
			.replace(":", "")
			.replace(".", "")
			+ String.format("%08d", inventoryReviewedItem.getItemId());
	}
}
