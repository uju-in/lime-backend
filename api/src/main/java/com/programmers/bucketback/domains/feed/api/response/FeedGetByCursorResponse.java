package com.programmers.bucketback.domains.feed.api.response;

import java.util.List;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.feed.model.FeedCursorSummaryLike;

public record FeedGetByCursorResponse(
	String nextCursorId,

	List<FeedCursorSummaryLike> feeds
) {
	public static FeedGetByCursorResponse from(final CursorSummary<FeedCursorSummaryLike> cursorSummary) {
		return new FeedGetByCursorResponse(
			cursorSummary.nextCursorId(),
			cursorSummary.summaries()
		);
	}
}
