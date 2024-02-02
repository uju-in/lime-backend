package com.programmers.lime.domains.review.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.review.domain.Review;
import com.programmers.lime.domains.review.model.ReviewContent;
import com.programmers.lime.domains.review.repository.ReviewRepository;

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
		Review savedReview = reviewRepository.save(review);

		return savedReview.getId();
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
