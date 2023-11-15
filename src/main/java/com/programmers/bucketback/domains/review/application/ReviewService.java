package com.programmers.bucketback.domains.review.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.review.application.dto.ReviewCreateServiceResponse;
import com.programmers.bucketback.domains.review.application.dto.ReviewGetByCursorServiceResponse;
import com.programmers.bucketback.domains.review.application.vo.ReviewContent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewAppender reviewAppender;
	private final ReviewModifier reviewModifier;
	private final ReviewValidator reviewValidator;
	private final ReviewCursorReader reviewCursorReader;
	private final ReviewRemover reviewRemover;

	public ReviewCreateServiceResponse createReview(
		final Long itemId,
		final ReviewContent reviewContent
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		Long reviewId = reviewAppender.append(itemId, memberId, reviewContent);

		return new ReviewCreateServiceResponse(reviewId);
	}

	public void updateReview(
		final Long itemId,
		final Long reviewId,
		final ReviewContent reviewContent
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		reviewValidator.validItemReview(itemId, reviewId);
		reviewValidator.validOwner(reviewId, memberId);
		reviewModifier.modify(reviewId, reviewContent);
	}

	public ReviewGetByCursorServiceResponse getReviewsByCursor(
		final Long itemId,
		final CursorPageParameters parameters
	) {
		return reviewCursorReader.readByCursor(itemId, parameters);
	}

	public void deleteReview(
		final Long itemId,
		final Long reviewId
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		reviewValidator.validItemReview(itemId, reviewId);
		reviewValidator.validOwner(reviewId, memberId);
		reviewRemover.remove(reviewId);
	}
}
