package com.programmers.lime.domains.comment.application;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.comment.domain.Comment;
import com.programmers.lime.domains.comment.implementation.CommentReader;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentValidator {

	private final CommentReader commentReader;

	public void validCommentInFeed(
		final Long feedId,
		final Long commentId
	) {
		Comment comment = commentReader.read(commentId);
		if (!comment.isInFeed(feedId)) {
			throw new BusinessException(ErrorCode.COMMENT_NOT_IN_FEED);
		}
	}

	public void validCommentOwner(
		final Long commentId,
		final Long memberId
	) {
		Comment comment = commentReader.read(commentId);
		if (!comment.isOwner(memberId)) {
			throw new BusinessException(ErrorCode.COMMENT_NOT_MINE);
		}
	}
}
