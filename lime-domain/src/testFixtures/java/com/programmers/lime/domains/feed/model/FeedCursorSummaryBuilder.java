package com.programmers.lime.domains.feed.model;

import java.util.List;
import java.util.stream.LongStream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedCursorSummaryBuilder {

	public static FeedCursorSummary build(final Long feedId) {
		return FeedCursorSummary.builder()
			.cursorId("202301010000000000000" + feedId)
			.feedId(feedId)
			.content("content" + feedId)
			.build();
	}

	public static List<FeedCursorSummary> buildMany() {
		return LongStream.range(1, 11)
			.mapToObj(FeedCursorSummaryBuilder::build)
			.toList();
	}
}
