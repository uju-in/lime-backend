package com.programmers.lime.domains.feed.api.response;

import java.util.List;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.feed.model.FeedCursorSummaryLike;

public record FeedGetByCursorResponse(
	String nextCursorId,
	int totalCount,
	List<FeedCursorSummaryLike> feeds
) {
	public static FeedGetByCursorResponse from(final CursorSummary<FeedCursorSummaryLike> cursorSummary) {
		return new FeedGetByCursorResponse(
			cursorSummary.nextCursorId(),
			cursorSummary.summaryCount(),
			cursorSummary.summaries()
		);
	}
}
