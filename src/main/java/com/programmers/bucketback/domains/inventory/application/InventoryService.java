package com.programmers.bucketback.domains.inventory.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.bucket.application.vo.ItemIdRegistry;
import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryGetReviewedItemResponse;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryInfoSummary;
import com.programmers.bucketback.domains.inventory.application.dto.InventoryGetServiceResponse;
import com.programmers.bucketback.domains.member.application.MemberReader;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final InventoryAppender inventoryAppender;
	private final InventoryReader inventoryReader;
	private final InventoryModifier inventoryModifier;
	private final InventoryRemover inventoryRemover;
	private final MemberReader memberReader;

	/** 인벤토리 생성 */
	public Long createInventory(
		final Hobby hobby,
		final ItemIdRegistry registry
	) {
		validateEmptyRegistry(registry);
		Long memberId = MemberUtils.getCurrentMemberId();
		validateDuplication(hobby, memberId);

		return inventoryAppender.append(memberId, hobby, registry);
	}

	/** 인벤토리 수정 */
	public void modifyInventory(
		final Long inventoryId,
		final ItemIdRegistry registry
	) {
		validateEmptyRegistry(registry);
		Long memberId = MemberUtils.getCurrentMemberId();

		inventoryModifier.modify(memberId, inventoryId, registry);
	}

	/** 인벤토리 삭제 */
	public void deleteInventory(final Long inventoryId) {
		Long memberId = MemberUtils.getCurrentMemberId();
		inventoryRemover.remove(inventoryId, memberId);
	}

	/**
	 * 인벤토리 상세 조회
	 */
	public InventoryGetServiceResponse getInventory(final Long inventoryId) {
		return inventoryReader.readDetail(inventoryId);
	}

	/**
	 * 인벤토리 목록 조회
	 */
	public List<InventoryInfoSummary> getInventories(final String nickname) {
		Long memberId = memberReader.readByNickname(nickname).getId();

		return inventoryReader.readSummary(memberId);
	}

	/** 인벤토리  수정을 위한 내가 리뷰한 아이템 목록 조회  */
	public InventoryGetReviewedItemResponse getReviewedItemsForModify(
		final Long inventoryId,
		final CursorPageParameters parameters
	) {
		InventorReviewedItemCursorSummary inventorReviewedItemCursorSummary =
			inventoryReader.readReviewedItem(inventoryId, parameters);

		return new InventoryGetReviewedItemResponse(inventorReviewedItemCursorSummary);
	}

	private void validateDuplication(
		final Hobby hobby,
		final Long memberId
	) {
		if (inventoryReader.isCreated(hobby, memberId)) {
			throw new BusinessException(ErrorCode.INVENTORY_ALREADY_EXIST);
		}
	}

	private void validateEmptyRegistry(final ItemIdRegistry registry) {
		if (registry.itemIds().isEmpty()) {
			throw new BusinessException(ErrorCode.INVENTORY_ITEM_NOT_REQUESTED);
		}
	}

}
