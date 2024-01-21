package com.programmers.lime.domains.friendships.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.friendships.repository.FriendshipRepository;
import com.programmers.lime.domains.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FriendshipRemover {

	private final FriendshipRepository friendshipRepository;
	private final FriendshipReader friendshipReader;

	public void remove(
		final Member toMember,
		final Member fromMember
	) {
		final Friendship friendship = friendshipReader.read(toMember, fromMember);
		friendshipRepository.delete(friendship);
	}
}
