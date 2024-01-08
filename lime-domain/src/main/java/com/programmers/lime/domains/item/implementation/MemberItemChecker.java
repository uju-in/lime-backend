package com.programmers.lime.domains.item.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.repository.MemberItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberItemChecker {

	private final MemberItemRepository memberItemRepository;

	public boolean existMemberItemByMemberId(
		final Long memberId,
		final Item item
	) {
		return memberItemRepository.
			existsMemberItemByMemberIdAndItem(
				memberId,
				item
			);
	}
}
