package com.programmers.bucketback.domains.comment.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentAppender commentAppender;
	private final CommentValidator commentValidator;
	private final CommentModifier commentModifier;

	public void createComment(
		final Long feedId,
		final String content
	) {
		commentAppender.append(feedId, content);
	}

	public void modifyComment(
		final Long feedId,
		final Long commentId,
		final String content
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		commentValidator.validCommentInFeed(feedId, commentId);
		commentValidator.validCommentOwner(commentId, memberId);
		commentModifier.modify(commentId, content);
	}
}
