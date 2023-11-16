package com.programmers.bucketback.domains.feed.api.response;

import java.util.List;

import com.programmers.bucketback.domains.feed.application.dto.response.FeedGetByCursorServiceResponse;
import com.programmers.bucketback.domains.feed.model.FeedCursorSummaryLike;

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
