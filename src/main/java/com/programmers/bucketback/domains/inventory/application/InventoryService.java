package com.programmers.bucketback.domains.inventory.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.review.application.ReviewReader;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final InventoryAppender inventoryAppender;
	private final InventoryReader inventoryReader;
	private final ReviewReader reviewReader;

	public void createInventory(
		final InventoryContent content
	) {
		//취미별로 중복되면 안됨
		Long memberId = MemberUtils.getCurrentMemberId();
		if (inventoryReader.isCreated(content.hobby(),memberId)){
			throw new BusinessException(ErrorCode.INVENTORY_ALREADY_EXIST); // 이미 생성된 인벤토리가 있습니다.
		}

		//리뷰한 아이템만 등록 가능
		// 내가 리뷰한 아이템을 조회하는 방법  : item에서 리뷰들을 조회 리뷰들에서 작성자가 맞는지 확인 isOwner활용?
		// 아이템별로 내가 리뷰한 아이템인지 확인해야함 => itemValidator 로 확인
		// if (reviewReader.read())
		// reviewRepository에서 memberId로 리뷰한 모든아이템들을 조회해야함 그걸 반환하는 api있으면 됨
		inventoryAppender.append(memberId,content);
	}
}
