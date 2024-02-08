package com.programmers.lime.domains.review.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.review.domain.Review;
import com.programmers.lime.domains.review.repository.ReviewLikeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewLikeRemover {

	private final ReviewReader reviewReader;
	private final ReviewLikeRepository reviewLikeRepository;

	public void deleteByMemberIdAndReviewId(
		final Long memberId,
		final Long reviewId
	) {
		Review review = reviewReader.read(reviewId);
		reviewLikeRepository.deleteReviewLikeByMemberIdAndReview(memberId, review);
	}

	public void deleteByReviewId(
		final Long reviewId
	) {
		reviewLikeRepository.deleteReviewLikeByReviewId(reviewId);
	}
}
