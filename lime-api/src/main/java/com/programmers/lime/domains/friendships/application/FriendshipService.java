package com.programmers.lime.domains.friendships.application;

import org.springframework.stereotype.Service;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.friendships.implementation.FriendshipAppender;
import com.programmers.lime.domains.friendships.implementation.FriendshipReader;
import com.programmers.lime.domains.friendships.implementation.FriendshipRemover;
import com.programmers.lime.domains.friendships.model.FriendshipSummary;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendshipService {

	private final FriendshipAppender friendshipAppender;
	private final FriendshipRemover friendshipRemover;
	private final FriendshipReader friendshipReader;
	private final MemberUtils memberUtils;
	private final MemberReader memberReader;

	public void follow(final String nickname) {
		final Member fromMember = memberUtils.getCurrentMember();
		final Member toMember = memberReader.readByNickname(nickname);

		friendshipAppender.append(toMember, fromMember);
	}

	public void unfollow(final String nickname) {
		final Member fromMember = memberUtils.getCurrentMember();
		final Member toMember = memberReader.readByNickname(nickname);

		friendshipRemover.remove(toMember, fromMember);
	}

	public CursorSummary<FriendshipSummary> getFollower(
		final String nickname,
		final CursorPageParameters parameters
	) {
		return friendshipReader.readFollowerByCursor(nickname, parameters);
	}

	public CursorSummary<FriendshipSummary> getFollowing(
		final String nickname,
		final CursorPageParameters parameters
	) {
		return friendshipReader.readFollowingByCursor(nickname, parameters);
	}
}
