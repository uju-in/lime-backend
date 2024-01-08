package com.programmers.lime.domains.feed.repository;

import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.feed.model.FeedCursorSummary;
import com.programmers.lime.domains.feed.model.FeedSortCondition;

public interface FeedRepositoryForCursor {
	List<FeedCursorSummary> findAllByCursor(
		final Long nicknameMemberId,
		final boolean onlyNicknameLikeFeeds,
		final Hobby hobby,
		final FeedSortCondition feedSortCondition,
		final String cursorId,
		final int pageSize
	);
}
