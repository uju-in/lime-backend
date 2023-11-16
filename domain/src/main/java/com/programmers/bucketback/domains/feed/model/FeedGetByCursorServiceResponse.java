package com.programmers.bucketback.domains.feed.model;

import java.util.List;

public record FeedGetByCursorServiceResponse(
	String nextCursorId,

	List<FeedCursorSummaryLike> feeds
) {
	public FeedGetByCursorResponse toFeedGetByCursorResponse() {
		return new FeedGetByCursorResponse(
			nextCursorId,
			feeds
		);
	}
}
