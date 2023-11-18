package com.programmers.bucketback.domains.review.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.review.domain.Review;
import com.programmers.bucketback.domains.review.model.ReviewContent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewModifier {

	private final ReviewReader reviewReader;

	@Transactional
	public void modify(
		final Long reviewId,
		final ReviewContent reviewContent
	) {
		final Review review = reviewReader.read(reviewId);
		review.changeReviewContent(reviewContent);
	}
}
