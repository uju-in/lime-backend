package com.programmers.lime.domains.friendships.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.friendships.repository.FriendshipRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FriendshipAppender {

	private final FriendshipRepository friendshipRepository;

	public Friendship append(
		final Long toMemberId,
		final Long fromMemberId
	) {
		final Friendship friendship = new Friendship(toMemberId, fromMemberId);

		return friendshipRepository.save(friendship);
	}
}
