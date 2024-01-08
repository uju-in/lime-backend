package com.programmers.lime.domains.feed.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedCursorSummaryLikeBuilder {

	public static FeedCursorSummaryLike build(
		final FeedCursorSummary feedCursorSummary,
		final boolean isLike
	) {
		return feedCursorSummary.of(isLike);
	}

	public static List<FeedCursorSummaryLike> buildMany(
		final List<FeedCursorSummary> feedCursorSummaries,
		final boolean isLike
	) {
		return feedCursorSummaries.stream()
			.map(feedCursorSummary -> build(feedCursorSummary, isLike))
			.toList();
	}
}
