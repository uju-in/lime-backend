package com.programmers.lime.domains.friendships.repository;

import java.util.List;

import com.programmers.lime.domains.friendships.model.FriendshipSummary;

public interface FriendshipRepositoryForCursor {
	List<FriendshipSummary> findFollowerByCursor(
		final String nickname,
		final String nextCursorId,
		final int pageSize
	);

	List<FriendshipSummary> findFollowingByCursor(
		final String nickname,
		final String nextCursorId,
		final int pageSize
	);
}
