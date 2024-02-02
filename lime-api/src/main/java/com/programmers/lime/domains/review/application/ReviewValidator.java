package com.programmers.lime.domains.review.application;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.review.domain.Review;
import com.programmers.lime.domains.review.implementation.ReviewReader;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

	private final ReviewReader reviewReader;

	public void validItemReview(
		final Long itemId,
		final Long reviewId
	) {
		Review review = reviewReader.read(reviewId);
		if (!review.containsItem(itemId)) {
			throw new BusinessException(ErrorCode.REVIEW_NOT_EQUAL_ITEM);
		}
	}

	public void validOwner(
		final Long reviewId,
		final Long memberId
	) {
		Review review = reviewReader.read(reviewId);
		if (!review.isOwner(memberId)) {
			throw new BusinessException(ErrorCode.REVIEW_NOT_MINE);
		}
	}

	public void validIsMemberAlreadyReviewed(
		final Long itemId,
		final Long memberId
	) {
		if (reviewReader.existsByItemIdAndMemberId(itemId, memberId)) {
			throw new BusinessException(ErrorCode.REVIEW_ALREADY_EXIST);
		}
	}
}
