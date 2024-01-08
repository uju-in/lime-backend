package com.programmers.lime.domains.comment.domain;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.lime.domains.feed.FeedBuilder;
import com.programmers.lime.domains.feed.domain.Feed;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentBuilder {
	public static Comment build() {
		final Feed feed = FeedBuilder.build();
		return build(feed, "이거 좀 짱인듯?");
	}

	public static Comment build(final String content) {
		final Feed feed = FeedBuilder.build();
		return build(feed, content);
	}

	public static Comment build(
		final Feed feed,
		final String content
	) {
		final Comment comment = new Comment(feed, 1L, content);

		setCommentId(comment);

		return comment;
	}

	private static void setCommentId(final Comment comment) {
		ReflectionTestUtils.setField(
			comment,
			"id",
			1L
		);
	}
}
