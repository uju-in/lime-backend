package com.programmers.lime.domains.inventory.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.domains.inventory.implementation.InventoryAppender;
import com.programmers.lime.domains.inventory.implementation.InventoryModifier;
import com.programmers.lime.domains.inventory.implementation.InventoryReader;
import com.programmers.lime.domains.inventory.implementation.InventoryRemover;
import com.programmers.lime.domains.inventory.model.InventoryGetServiceResponse;
import com.programmers.lime.domains.inventory.model.InventoryInfoSummary;
import com.programmers.lime.domains.inventory.model.InventoryReviewItemSummary;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final InventoryAppender inventoryAppender;
	private final InventoryReader inventoryReader;
	private final InventoryModifier inventoryModifier;
	private final InventoryRemover inventoryRemover;
	private final MemberReader memberReader;
	private final MemberUtils memberUtils;

	/** 인벤토리 생성 */
	public Long createInventory(
		final Hobby hobby,
		final ItemIdRegistry registry
	) {
		validateEmptyRegistry(registry);
		Long memberId = memberUtils.getCurrentMemberId();
		validateDuplication(hobby, memberId);

		return inventoryAppender.append(memberId, hobby, registry);
	}

	/** 인벤토리 수정 */
	public void modifyInventory(
		final Long inventoryId,
		final ItemIdRegistry registry
	) {
		validateEmptyRegistry(registry);
		Long memberId = memberUtils.getCurrentMemberId();

		inventoryModifier.modify(memberId, inventoryId, registry);
	}

	/** 인벤토리 삭제 */
	public void deleteInventory(final Long inventoryId) {
		Long memberId = memberUtils.getCurrentMemberId();
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

	/**
	 * 인벤토리  수정을 위한 내가 리뷰한 아이템 목록 조회
	 */
	public CursorSummary<InventoryReviewItemSummary> getReviewedItemsForModify(
		final Long inventoryId,
		final Hobby hobby,
		final CursorPageParameters parameters
	) {
		Long memberId = memberUtils.getCurrentMemberId();

		return inventoryReader.readReviewedItem(
			memberId,
			inventoryId,
			hobby,
			parameters
		);
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
