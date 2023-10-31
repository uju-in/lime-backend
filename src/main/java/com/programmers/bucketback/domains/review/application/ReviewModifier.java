package com.programmers.bucketback.domains.review.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.review.application.dto.ReviewContent;
import com.programmers.bucketback.domains.review.domain.Review;
import com.programmers.bucketback.domains.review.repository.ReviewRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewModifier {

	private final ReviewRepository reviewRepository;

	@Transactional
	public void modify(
		final Long reviewId,
		final ReviewContent reviewContent
	) {
		final Review review = reviewRepository.findById(reviewId).orElseThrow(
			() -> new EntityNotFoundException(ErrorCode.REVIEW_NOT_FOUND)
		);

		review.changeReviewContent(reviewContent);
	}
}
