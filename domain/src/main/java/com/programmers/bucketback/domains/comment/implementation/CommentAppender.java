package com.programmers.bucketback.domains.comment.implementation;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.comment.domain.Comment;
import com.programmers.bucketback.domains.comment.repository.CommentRepository;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.implementation.FeedReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentAppender {

	private final CommentRepository commentRepository;
	private final FeedReader feedReader;

	public void append(
		final Long feedId,
		final String content,
		final Long memberId
	) {
		final Feed feed = feedReader.read(feedId);
		final Comment comment = new Comment(feed, memberId, content);

		commentRepository.save(comment);
	}
}
