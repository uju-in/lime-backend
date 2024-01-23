package com.programmers.lime.domains.friendships.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.friendships.repository.FriendshipRepository;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FriendshipAppender {

	private final FriendshipRepository friendshipRepository;

	public Friendship append(
		final Member toMember,
		final Member fromMember
	) {
		if (friendshipRepository.existsByToMemberAndFromMember(toMember, fromMember)) {
			throw new BusinessException(ErrorCode.FRIENDSHIP_ALREADY_EXISTS);
		}

		final Friendship friendship = new Friendship(toMember, fromMember);

		return friendshipRepository.save(friendship);
	}
}
