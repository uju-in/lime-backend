package com.programmers.lime.domains.friendships.repository;

import java.util.List;

import com.programmers.lime.domains.friendships.model.FriendshipSummary;

public interface FriendshipRepositoryForCursor {
	List<FriendshipSummary> findAllByCursor(
		final String nickname,
		final String nextCursorId,
		final int pageSize
	);
}
