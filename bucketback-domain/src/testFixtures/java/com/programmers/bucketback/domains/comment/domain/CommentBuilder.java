package com.programmers.bucketback.domains.comment.domain;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.bucketback.domains.feed.FeedBuilder;
import com.programmers.bucketback.domains.feed.domain.Feed;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentBuilder {
	public static Comment build() {
		final Feed feed = FeedBuilder.build();
		final Comment comment = new Comment(feed, 1L, "이거 좀 짱인듯?");

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
