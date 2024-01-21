package com.programmers.lime.domains.friendships.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.friendships.repository.FriendshipRepository;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FriendshipAppender {

	private final FriendshipRepository friendshipRepository;

	public Friendship append(
		final Long toMemberId,
		final Long fromMemberId
	) {
		if (friendshipRepository.existsByToMemberIdAndFromMemberId(toMemberId, fromMemberId)) {
			throw new EntityNotFoundException(ErrorCode.FRIENDSHIP_ALREADY_EXISTS);
		}

		final Friendship friendship = new Friendship(toMemberId, fromMemberId);

		return friendshipRepository.save(friendship);
	}
}
