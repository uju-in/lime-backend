package com.programmers.lime.domains.review.application;

import org.springframework.stereotype.Service;

import com.programmers.lime.domains.review.implementation.ReviewLikeAppender;
import com.programmers.lime.domains.review.implementation.ReviewLikeRemover;
import com.programmers.lime.domains.review.implementation.ReviewLikeValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

	private final ReviewLikeAppender reviewLikeAppender;
	private final ReviewLikeRemover reviewLikeRemover;
	private final ReviewLikeValidator reviewLikeValidator;

	public void like(
		final Long memberId,
		final Long reviewId
	) {
		reviewLikeValidator.validateReviewLike(memberId, reviewId);
		reviewLikeAppender.append(memberId, reviewId);
	}

	public void unlike(
		final Long memberId,
		final Long reviewId
	) {
		reviewLikeValidator.validateReviewUnlike(memberId, reviewId);
		reviewLikeRemover.deleteByMemberIdAndReviewId(memberId, reviewId);
	}
}
