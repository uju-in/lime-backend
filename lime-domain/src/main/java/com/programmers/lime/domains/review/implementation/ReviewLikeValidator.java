package com.programmers.lime.domains.review.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewLikeValidator {

	private final ReviewLikeReader reviewLikeReader;

	public void validateReviewLike(
		final Long memberId,
		final Long reviewId
	) {
		if (reviewLikeReader.alreadyLiked(memberId, reviewId)) {
			throw new BusinessException(ErrorCode.ALREADY_REVIEW_LIKED);
		}
	}

	public void validateReviewUnlike(
		final Long memberId,
		final Long reviewId
	) {
		if (!reviewLikeReader.alreadyLiked(memberId, reviewId)) {
			throw new BusinessException(ErrorCode.NOT_REVIEW_LIKED);
		}
	}
}
