package com.programmers.bucketback.domains.comment.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.comment.domain.Comment;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentModifier {

	private final CommentReader commentReader;

	@Transactional
	public void modify(
		final Long commentId,
		final String content
	) {
		final Comment comment = commentReader.read(commentId);
		comment.changeContent(content);
	}
}
