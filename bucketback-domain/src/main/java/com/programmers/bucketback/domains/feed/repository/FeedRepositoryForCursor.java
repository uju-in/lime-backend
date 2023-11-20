package com.programmers.bucketback.domains.feed.repository;

import java.util.List;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.feed.model.FeedCursorSummary;
import com.programmers.bucketback.domains.feed.model.FeedSortCondition;

public interface FeedRepositoryForCursor {
	List<FeedCursorSummary> findAllByCursor(
		final Long myPageMemberId,
		final Hobby hobby,
		final FeedSortCondition feedSortCondition,
		final String cursorId,
		final int pageSize
	);
}
