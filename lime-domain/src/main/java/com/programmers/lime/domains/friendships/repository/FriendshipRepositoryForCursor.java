package com.programmers.lime.domains.friendships.repository;

import java.util.List;

import com.programmers.lime.domains.friendships.model.FollowerSummary;

public interface FriendshipRepositoryForCursor {
	List<FollowerSummary> findAllByCursor(
		final String nickname,
		final String nextCursorId,
		final int pageSize
	);
}
