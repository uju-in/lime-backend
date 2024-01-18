package com.programmers.lime.domains.friendships.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.friendships.repository.FriendshipRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FriendshipCounter {

	private final FriendshipRepository friendshipRepository;

	@Transactional(readOnly = true)
	public long countFollower(final Long memberId) {
		return friendshipRepository.countByToMemberId(memberId);
	}

	@Transactional(readOnly = true)
	public long countFollowing(final Long memberId) {
		return friendshipRepository.countByFromMemberId(memberId);
	}
}
