package com.programmers.bucketback.domains.review.implementation;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.review.domain.Review;
import com.programmers.bucketback.domains.review.model.ReviewContent;
import com.programmers.bucketback.domains.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewAppender {

	private final ReviewRepository reviewRepository;

	public Long append(
		final Long itemId,
		final Long memberId,
		final ReviewContent reviewContent
	) {
		Review review = getReview(memberId, reviewContent, itemId);
		reviewRepository.save(review);

		return review.getId();
	}

	private Review getReview(
		final Long memberId,
		final ReviewContent reviewContent,
		final Long itemId
	) {
		return Review.builder()
			.itemId(itemId)
			.rating(reviewContent.rating())
			.content(reviewContent.content())
			.memberId(memberId)
			.build();
	}
}
