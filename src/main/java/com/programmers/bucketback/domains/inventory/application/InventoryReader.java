package com.programmers.bucketback.domains.inventory.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryInfoSummary;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryItemGetResponse;
import com.programmers.bucketback.domains.inventory.application.dto.GetInventoryServiceResponse;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
import com.programmers.bucketback.domains.inventory.domain.InventoryItem;
import com.programmers.bucketback.domains.inventory.repository.InventoryItemRepository;
import com.programmers.bucketback.domains.inventory.repository.InventoryRepository;
import com.programmers.bucketback.domains.item.application.ItemReader;
import com.programmers.bucketback.domains.item.application.vo.ItemInfo;
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

	/** 인벤토링 아이템 조회 */
	public List<InventoryItem> inventoryItemRead(final Long inventoryId) {
		return inventoryItemRepository.findByInventoryId(inventoryId);
	}

	/**
	 * 인벤토링 아이템 상세 조회
	 */
	public GetInventoryServiceResponse readDetail(final Long inventoryId) {
		Inventory inventory = read(inventoryId);

		List<InventoryItemGetResponse> inventoryItemGetResponses = inventory.getInventoryItems().stream()
			.map(inventoryItem -> itemReader.read(inventoryItem.getItem().getId()))
			.map(item -> new InventoryItemGetResponse(ItemInfo.from(item), item.getUrl()))
			.toList();

		return GetInventoryServiceResponse.of(inventory, inventoryItemGetResponses);
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
	public InventorReviewedItemCursorSummary readReviewedItem(
		final Long inventoryId,
		final CursorPageParameters parameters
	) {
		int pageSize = parameters.size() == 0 ? 20 : parameters.size();

		Long memberId = MemberUtils.getCurrentMemberId();
		List<Long> itemIdsFromInventory = read(inventoryId, memberId)
			.getInventoryItems().stream()
			.map(inventoryItem -> inventoryItem.getItem().getId())
			.toList();

		List<Review> reviews = reviewReader.readByMemberId(memberId);
		List<Long> itemIdsFromReview = reviews.stream()
			.map(review -> review.getItemId())
			.toList();

		List<InventoryReviewItemSummary> summaries = itemReader.readReviewedItem(
			itemIdsFromReview,
			itemIdsFromInventory,
			parameters.cursorId(),
			pageSize
		);

		String nextCursorId = summaries.size() == 0 ? null : summaries.get(summaries.size() - 1).cursorId();
		int summaryCount = summaries.size();

		return new InventorReviewedItemCursorSummary(nextCursorId, summaryCount, summaries);
	}

}
