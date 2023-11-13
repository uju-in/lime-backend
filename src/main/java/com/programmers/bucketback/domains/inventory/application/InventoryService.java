package com.programmers.bucketback.domains.inventory.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.application.vo.ItemIdRegistry;
import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoriesGetResponse;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryGetResponse;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryGetReviewedItemResponse;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryInfoSummary;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryItemGetResponse;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
import com.programmers.bucketback.domains.item.application.ItemReader;
import com.programmers.bucketback.domains.item.application.vo.ItemInfo;
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
	private final ItemReader itemReader;

	/** 인벤토리 생성 */
	public void createInventory(
		final Hobby hobby,
		final ItemIdRegistry registry
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		validateDuplication(hobby, memberId);

		inventoryAppender.append(memberId, hobby, registry);
	}

	/** 인벤토리 수정 */
	@Transactional
	public void modifyInventory(
		final Long inventoryId,
		final ItemIdRegistry itemIdRegistry
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		Inventory inventory = inventoryReader.read(inventoryId, memberId);

		inventoryModifier.modify(inventory, itemIdRegistry);
	}

	/** 인벤토리 삭제 */
	public void deleteInventory(final Long inventoryId) {
		Long memberId = MemberUtils.getCurrentMemberId();
		inventoryRemover.remove(inventoryId, memberId);
	}

	/** 인벤토리 상세 조회 */
	@Transactional(readOnly = true)
	public InventoryGetResponse getInventory(final Long inventoryId) {
		Inventory inventory = inventoryReader.read(inventoryId);

		List<InventoryItemGetResponse> inventoryItemGetResponses = inventory.getInventoryItems().stream()
			.map(inventoryItem -> itemReader.read(inventoryItem.getItem().getId()))
			.map(item -> new InventoryItemGetResponse(ItemInfo.from(item), item.getUrl()))
			.toList();

		return InventoryGetResponse.of(inventory, inventoryItemGetResponses);
	}

	/** 인벤토리 목록 조회 */
	public InventoriesGetResponse getInventories(final String nickname) {
		List<InventoryInfoSummary> inventoryInfoSummaries = inventoryReader.readSummary(nickname);

		return InventoriesGetResponse.from(inventoryInfoSummaries);
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

}
