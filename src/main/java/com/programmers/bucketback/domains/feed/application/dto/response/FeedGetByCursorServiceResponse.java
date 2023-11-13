package com.programmers.bucketback.domains.feed.application.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.feed.api.response.FeedGetByCursorResponse;
import com.programmers.bucketback.domains.feed.application.vo.FeedCursorSummaryLike;

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
