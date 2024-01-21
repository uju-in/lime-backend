package com.programmers.lime.domains.friendship.domain;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.member.domain.Member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FriendshipBuilder {
	public static Friendship build(
		final Member toMember,
		final Member fromMember
	) {
		final Friendship friendship = new Friendship(toMember, fromMember);
		setId(friendship, 1L);

		return friendship;
	}

	private static void setId(
		final Friendship friendship,
		final Long id
	) {
		ReflectionTestUtils.setField(
			friendship,
			"id",
			id
		);
	}
}
