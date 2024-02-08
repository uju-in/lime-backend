package com.programmers.lime.domains.item.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.repository.MemberItemRepository;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

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

	public void validateExistMemberIdAndMemberItemId(
		final Long memberId,
		final List<Long> memberItemIds
	) {
		memberItemIds.forEach(memberItemId -> {
			boolean isExistsMemberItem = memberItemRepository.existsMemberItemByIdAndMemberId(memberItemId, memberId);

			if (!isExistsMemberItem) {
				throw new BusinessException(ErrorCode.MEMBER_ITEM_NOT_FOUND);
			}
		});
	}
}
