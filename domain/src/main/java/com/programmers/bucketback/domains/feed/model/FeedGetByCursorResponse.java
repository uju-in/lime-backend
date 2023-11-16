package com.programmers.bucketback.domains.feed.model;

import java.util.List;

public record FeedGetByCursorResponse(
	String nextCursorId,

	List<FeedCursorSummaryLike> feeds
) {
	public static FeedGetByCursorResponse from(final FeedGetByCursorServiceResponse response) {
		return new FeedGetByCursorResponse(
			response.nextCursorId(),
			response.feeds()
		);
	}
}
