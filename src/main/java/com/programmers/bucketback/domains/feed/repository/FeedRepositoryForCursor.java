package com.programmers.bucketback.domains.feed.repository;

import java.util.List;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.feed.application.FeedSortCondition;
import com.programmers.bucketback.domains.feed.application.vo.FeedCursorSummary;

public interface FeedRepositoryForCursor {
	List<FeedCursorSummary> findAllByCursor(
		final Long myPageMemberId,
		final Hobby hobby,
		final FeedSortCondition feedSortCondition,
		final String cursorId,
		final int pageSize
	);
}
