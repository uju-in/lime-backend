package com.programmers.bucketback.domains.comment.application;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentAppender commentAppender;

	public void createComment(
		final Long feedId,
		final String content
	) {
		commentAppender.append(feedId, content);
	}
}
