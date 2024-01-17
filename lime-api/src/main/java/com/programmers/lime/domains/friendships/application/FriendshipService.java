package com.programmers.lime.domains.friendships.application;

import org.springframework.stereotype.Service;

import com.programmers.lime.domains.friendships.implementation.FriendshipAppender;
import com.programmers.lime.domains.friendships.implementation.FriendshipRemover;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendshipService {

	private final FriendshipAppender friendshipAppender;
	private final FriendshipRemover friendshipRemover;
	private final MemberUtils memberUtils;
	private final MemberReader memberReader;

	public void follow(final String nickname) {
		final Long fromMemberId = memberUtils.getCurrentMemberId();
		final Long toMemberId = memberReader.readByNickname(nickname).getId();

		friendshipAppender.append(toMemberId, fromMemberId);
	}

	public void unfollow(final String nickname) {
		final Long fromMemberId = memberUtils.getCurrentMemberId();
		final Long toMemberId = memberReader.readByNickname(nickname).getId();

		friendshipRemover.remove(toMemberId, fromMemberId);
	}
}
