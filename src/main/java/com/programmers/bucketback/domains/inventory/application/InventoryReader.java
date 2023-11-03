package com.programmers.bucketback.domains.inventory.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
import com.programmers.bucketback.domains.inventory.domain.InventoryItem;
import com.programmers.bucketback.domains.inventory.repository.InventoryItemRepository;
import com.programmers.bucketback.domains.inventory.repository.InventoryRepository;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryReader {

	private final InventoryRepository inventoryRepository;
	private final InventoryItemRepository inventoryItemRepository;

	public boolean isCreated(
		final Hobby hobby,
		final Long memberId
	) {
		return inventoryRepository.existsByHobbyAndMemberId(hobby, memberId);
	}

	/** 인벤토링 조회 */
	public Inventory read(
		final Long inventoryId,
		final Long memberId
	){
		return inventoryRepository.findByIdAndMemberId(inventoryId,memberId)
			.orElseThrow(()->{
				throw new BusinessException(ErrorCode.INVENTORY_NOT_FOUND);
			});
	}

	public Inventory read(final Long inventoryId){
		return inventoryRepository.findById(inventoryId)
			.orElseThrow(()->{
				throw new BusinessException(ErrorCode.INVENTORY_NOT_FOUND);
			});
	}

	/** 인벤토링 아이템 조회 */
	public List<InventoryItem> inventoryItemRead(final Long inventoryId) {
		return inventoryItemRepository.findByInventoryId(inventoryId)
			.orElseThrow(()-> {
				throw new BusinessException(ErrorCode.INVENTORY_ITEM_NOT_FOUND);
			});
	}
}
