package com.programmers.bucketback.domains.item.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.repository.MemberItemRepository;
import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemValidator {

	private final MemberItemRepository memberItemRepository;

	public void validateExistMemberItem(
		final Long memberId,
		final List<Item> items
	) {
		boolean isExistsMemberItem = memberItemRepository.existsByMemberIdAndItemsIn(memberId, items);

		if (isExistsMemberItem) {
			throw new BusinessException(ErrorCode.MEMBER_ITEM_ALREADY_EXIST);
		}
	}
}
