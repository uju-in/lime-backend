package com.programmers.lime.domains.comment.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.comment.domain.Comment;
import com.programmers.lime.domains.comment.repository.CommentRepository;
import com.programmers.lime.domains.feed.domain.Feed;
import com.programmers.lime.domains.feed.implementation.FeedReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentAppender {

	private final CommentRepository commentRepository;
	private final FeedReader feedReader;

	public Comment append(
		final Long feedId,
		final String content,
		final Long memberId
	) {
		final Feed feed = feedReader.read(feedId);
		final Comment comment = new Comment(feed, memberId, content);

		return commentRepository.save(comment);
	}
}
