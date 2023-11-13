package com.programmers.bucketback.domains.comment.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.comment.domain.Comment;
import com.programmers.bucketback.domains.feed.application.FeedReader;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentModifier {

	private final CommentReader commentReader;
	private final FeedReader feedReader;

	@Transactional
	public void modify(
		final Long commentId,
		final String content
	) {
		final Comment comment = commentReader.read(commentId);
		comment.changeContent(content);
	}

	@Transactional
	public void adopt(
		final Long feedId,
		final Long commentId,
		final Long memberId
	) {
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

		comment.adopt();
	}
}
