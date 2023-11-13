package com.programmers.bucketback.domains.comment.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.comment.api.dto.response.CommentGetCursorResponse;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentAppender commentAppender;
	private final CommentValidator commentValidator;
	private final CommentModifier commentModifier;
	private final CommentRemover commentRemover;
	private final CommentReader commentReader;

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
		final Long memberId = MemberUtils.getCurrentMemberId();
		commentValidator.validCommentInFeed(feedId, commentId);
		commentValidator.validCommentOwner(commentId, memberId);
		commentModifier.modify(commentId, content);
	}

	public void deleteComment(
		final Long feedId,
		final Long commentId
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		commentValidator.validCommentInFeed(feedId, commentId);
		commentValidator.validCommentOwner(commentId, memberId);
		commentRemover.remove(commentId);
	}

	public CommentGetCursorResponse getFeedComments(
		final Long feedId,
		final CursorPageParameters parameters
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		CommentCursorSummary commentCursorSummary = commentReader.readByCursor(feedId, memberId, parameters);

		return new CommentGetCursorResponse(commentCursorSummary);
	}

	public void adoptComment(
		final Long feedId,
		final Long commentId
	) {
		if (!MemberUtils.isLoggedIn()) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED);
		}
		final Long memberId = MemberUtils.getCurrentMemberId();

		commentModifier.adopt(feedId, commentId, memberId);
	}
}
