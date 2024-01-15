package com.programmers.lime.domains.friendships.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.friendships.repository.FriendshipRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FriendshipRemover {

	private final FriendshipRepository friendshipRepository;
	private final FriendshipReader friendshipReader;

	public void remove(
		final Long toMemberId,
		final Long fromMemberId
	) {
		final Friendship friendship = friendshipReader.read(toMemberId, fromMemberId);
		friendshipRepository.delete(friendship);
	}
}
