package com.programmers.lime.domains.friendship.model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.friendships.model.FriendshipSummary;
import com.programmers.lime.domains.member.model.MemberInfo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FriendshipSummaryBuilder {
	public static List<FriendshipSummary> buildFollower(final List<Friendship> friendships) {
		final List<FriendshipSummary> friendshipSummaries = new ArrayList<>();
		friendships.stream().forEach(friendship -> {
			final MemberInfo memberInfo = MemberInfo.builder()
				.memberId(friendship.getFromMember().getId())
				.nickname(friendship.getFromMember().getNickname())
				.profileImage(friendship.getFromMember().getProfileImage())
				.build();
			final FriendshipSummary friendshipSummary = new FriendshipSummary(memberInfo, generateCursorId(friendship));
			friendshipSummaries.add(friendshipSummary);
		});

		return friendshipSummaries;
	}

	public static List<FriendshipSummary> buildFollowing(final List<Friendship> friendships) {
		final List<FriendshipSummary> friendshipSummaries = new ArrayList<>();
		friendships.stream().forEach(friendship -> {
			final MemberInfo memberInfo = MemberInfo.builder()
				.memberId(friendship.getToMember().getId())
				.nickname(friendship.getToMember().getNickname())
				.profileImage(friendship.getToMember().getProfileImage())
				.build();
			final FriendshipSummary friendshipSummary = new FriendshipSummary(memberInfo, generateCursorId(friendship));
			friendshipSummaries.add(friendshipSummary);
		});

		return friendshipSummaries;
	}

	private static String generateCursorId(final Friendship friendship) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		final String formattedDate = friendship.getCreatedAt().format(formatter);

		return String.format("%s%s", formattedDate, String.format("%08d", friendship.getId()));
	}
}
