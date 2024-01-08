package com.programmers.lime.domains.comment.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.comment.domain.Comment;

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

	@Transactional
	public void adopt(final Comment comment) {
		comment.adopt();
	}
}
