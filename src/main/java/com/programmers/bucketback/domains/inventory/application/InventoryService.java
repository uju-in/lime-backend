package com.programmers.bucketback.domains.inventory.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.inventory.application.vo.InventoryCreateContent;
import com.programmers.bucketback.domains.inventory.application.vo.InventoryUpdateContent;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
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

	/** 인벤토리 생성 */
	public void createInventory(
		final InventoryCreateContent content
	) {
		//취미별로 중복되면 안됨(이미 생성된 인벤토리가 있는지 확인)
		Long memberId = MemberUtils.getCurrentMemberId();
		validateDuplication(content, memberId);

		inventoryAppender.append(memberId, content);
	}

	/** 인벤토리 수정 */
	public void modifyInventory(
		final Long inventoryId,
		final InventoryUpdateContent content
	){
		//취미별로 중복되면 안됨(이미 생성된 인벤토리가 있는지 확인)
		Long memberId = MemberUtils.getCurrentMemberId();
		Inventory inventory = inventoryReader.read(inventoryId, memberId);

		inventoryModifier.modify(inventory, content);
	}

	/** 인벤토리 삭제 */
	@Transactional
	public void deleteInventory(final Long inventoryId) {
		inventoryRemover.remove(inventoryId);
	}

	private void validateDuplication(
		final InventoryCreateContent content,
		final Long memberId
	) {
		if (inventoryReader.isCreated(content.hobby(), memberId)) {
			throw new BusinessException(ErrorCode.INVENTORY_ALREADY_EXIST);
		}
	}

}
