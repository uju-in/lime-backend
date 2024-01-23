package com.programmers.lime.domains.friendship.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.MemberBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FriendshipBuilder {
	public static List<Friendship> buildFollower(
		final Member member,
		final int pageSize
	) {
		final long nextMemberId = member.getId() + 1;
		final List<Friendship> friendships = new ArrayList<>();
		for (long i = nextMemberId; i < pageSize + nextMemberId; i++) {
			final Member fromMember = MemberBuilder.build(i);
			final Friendship friendship = FriendshipBuilder.build(member, fromMember, i);
			setCreatedAt(friendship);
			friendships.add(friendship);
		}

		return friendships;
	}

	public static List<Friendship> buildFollowing(
		final Member member,
		final int pageSize
	) {
		final long nextMemberId = member.getId() + 1;
		final List<Friendship> friendships = new ArrayList<>();
		for (long i = nextMemberId; i < pageSize + nextMemberId; i++) {
			final Member toMember = MemberBuilder.build(i);
			final Friendship friendship = FriendshipBuilder.build(toMember, member, i);
			setCreatedAt(friendship);
			friendships.add(friendship);
		}

		return friendships;
	}

	public static Friendship build(
		final Member toMember,
		final Member fromMember,
		final long id
	) {
		final Friendship friendship = build(toMember, fromMember);
		setId(friendship, id);

		return friendship;
	}

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

	private static void setCreatedAt(final Friendship friendship) {
		ReflectionTestUtils.setField(
			friendship,
			"createdAt",
			LocalDateTime.now()
		);
	}
}
