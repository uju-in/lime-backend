package com.programmers.bucketback.domains.feed.api.response;

import java.util.List;

import com.programmers.bucketback.domains.feed.application.vo.FeedCursorSummaryLike;

public record FeedGetByCursorResponse(
	String nextCursorId,

	List<FeedCursorSummaryLike> feeds
) {
}
