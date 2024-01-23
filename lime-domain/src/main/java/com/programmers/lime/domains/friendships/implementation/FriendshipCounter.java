package com.programmers.lime.domains.friendships.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.friendships.repository.FriendshipRepository;
import com.programmers.lime.domains.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FriendshipCounter {

	private final FriendshipRepository friendshipRepository;

	@Transactional(readOnly = true)
	public long countFollower(final Member member) {
		return friendshipRepository.countByToMember(member);
	}

	@Transactional(readOnly = true)
	public long countFollowing(final Member member) {
		return friendshipRepository.countByFromMember(member);
	}
}
