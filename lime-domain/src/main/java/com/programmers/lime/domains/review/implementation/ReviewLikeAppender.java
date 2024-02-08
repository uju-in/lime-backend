package com.programmers.lime.domains.review.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.review.domain.Review;
import com.programmers.lime.domains.review.domain.ReviewLike;
import com.programmers.lime.domains.review.repository.ReviewLikeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewLikeAppender {

	private final ReviewLikeRepository reviewLikeRepository;

	private final ReviewReader reviewReader;

	public void append(
		final Long memberId,
		final Long reviewId
	) {
		Review review = reviewReader.read(reviewId);

		ReviewLike reviewLike = new ReviewLike(
			memberId,
			review
		);

		reviewLikeRepository.save(reviewLike);
	}
}
