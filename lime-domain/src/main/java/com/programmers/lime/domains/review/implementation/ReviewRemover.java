package com.programmers.lime.domains.review.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.review.domain.Review;
import com.programmers.lime.domains.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewRemover {

	private final ReviewReader reviewReader;
	private final ReviewRepository reviewRepository;

	public void remove(final Long reviewId) {
		final Review review = reviewReader.read(reviewId);
		reviewRepository.delete(review);
	}
}
