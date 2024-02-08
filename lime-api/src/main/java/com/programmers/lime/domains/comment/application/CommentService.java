package com.programmers.lime.domains.comment.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.comment.application.dto.response.CommentGetCursorServiceResponse;
import com.programmers.lime.domains.comment.application.event.CommentCreateEvent;
import com.programmers.lime.domains.comment.domain.Comment;
import com.programmers.lime.domains.comment.implementation.CommentAppender;
import com.programmers.lime.domains.comment.implementation.CommentModifier;
import com.programmers.lime.domains.comment.implementation.CommentReader;
import com.programmers.lime.domains.comment.implementation.CommentRemover;
import com.programmers.lime.domains.comment.repository.CommentSummary;
import com.programmers.lime.domains.feed.domain.Feed;
import com.programmers.lime.domains.feed.implementation.FeedReader;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.sse.SsePayload;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.event.point.PointEvent;
import com.programmers.lime.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentAppender commentAppender;
	private final CommentValidator commentValidator;
	private final CommentModifier commentModifier;
	private final CommentRemover commentRemover;
	private final CommentReader commentReader;
	private final FeedReader feedReader;
	private final MemberUtils memberUtils;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional
	public void createComment(
		final Long feedId,
		final String content
	) {
		final Member commentWriter = memberUtils.getCurrentMember();
		final Comment comment = commentAppender.append(feedId, content, commentWriter.getId());

		applicationEventPublisher.publishEvent(new PointEvent(commentWriter.getId(), 5));

		SsePayload ssePayload = CommentCreateEvent.toSsePayload(commentWriter.getNickname(), comment);
		applicationEventPublisher.publishEvent(ssePayload);
	}

	public void modifyComment(
		final Long feedId,
		final Long commentId,
		final String content
	) {
		final Long memberId = memberUtils.getCurrentMemberId();
		commentValidator.validCommentInFeed(feedId, commentId);
		commentValidator.validCommentOwner(commentId, memberId);
		commentModifier.modify(commentId, content);
	}

	public void deleteComment(
		final Long feedId,
		final Long commentId
	) {
		final Long memberId = memberUtils.getCurrentMemberId();
		commentValidator.validCommentInFeed(feedId, commentId);
		commentValidator.validCommentOwner(commentId, memberId);
		commentRemover.remove(commentId);
	}

	public CommentGetCursorServiceResponse getFeedComments(
		final Long feedId,
		final CursorPageParameters parameters
	) {
		final Long memberId = memberUtils.getCurrentMemberId();

		int totalCommentCount = commentReader.countComments(feedId);
		CursorSummary<CommentSummary> cursorSummary = commentReader.readByCursor(
			feedId,
			memberId,
			parameters
		);

		return new CommentGetCursorServiceResponse(cursorSummary, totalCommentCount);
	}

	@Transactional
	public void adoptComment(
		final Long feedId,
		final Long commentId
	) {
		final Long memberId = memberUtils.getCurrentMemberId();

		final Feed feed = feedReader.read(feedId);
		if (!feed.isOwner(memberId)) {
			throw new BusinessException(ErrorCode.FORBIDDEN);
		}

		final Comment comment = commentReader.read(commentId);
		if (!comment.isInFeed(feedId)) {
			throw new BusinessException(ErrorCode.COMMENT_NOT_IN_FEED);
		}

		if (comment.isOwner(memberId)) {
			throw new BusinessException(ErrorCode.COMMENT_CANNOT_ADOPT);
		}

		commentModifier.adopt(comment);
		applicationEventPublisher.publishEvent(new PointEvent(comment.getMemberId(), 20));
	}
}
