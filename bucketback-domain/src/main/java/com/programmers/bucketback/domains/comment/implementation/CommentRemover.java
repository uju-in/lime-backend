package com.programmers.bucketback.domains.comment.implementation;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.comment.domain.Comment;
import com.programmers.bucketback.domains.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentRemover {

	private final CommentReader commentReader;
	private final CommentRepository commentRepository;

	public void remove(final Long commentId) {
		Comment comment = commentReader.read(commentId);
		commentRepository.delete(comment);
	}
}
