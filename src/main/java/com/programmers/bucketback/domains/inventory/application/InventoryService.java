package com.programmers.bucketback.domains.inventory.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final InventoryAppender inventoryAppender;
	private final InventoryReader inventoryReader;

	public void createInventory(
		final InventoryContent content
	) {
		//취미별로 중복되면 안됨(이미 생성된 인벤토리가 있는지 확인)
		Long memberId = MemberUtils.getCurrentMemberId();
		if (inventoryReader.isCreated(content.hobby(),memberId)){
			throw new BusinessException(ErrorCode.INVENTORY_ALREADY_EXIST);
		}

		inventoryAppender.append(memberId,content);
	}
}
