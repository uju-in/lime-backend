package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.repository.MemberItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberItemChecker {

	private final MemberItemRepository memberItemRepository;

	public boolean existMemberItemByMemberId(
		final Long memberId,
		final Item item
	) {
		return memberItemRepository.
			existsMemberItemByMembersIdAndItem(
				memberId,
				item
			);
	}
}
