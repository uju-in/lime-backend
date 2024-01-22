package com.programmers.lime.domains.review.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.review.domain.Review;
import com.programmers.lime.domains.review.repository.ReviewRepository;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewReader {

	private final ReviewRepository reviewRepository;

	public Review read(final Long reviewId) {
		return reviewRepository.findById(reviewId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.REVIEW_NOT_FOUND));
	}

	public List<Review> readByMemberId(final Long memberId) {
		return reviewRepository.findByMemberId(memberId);
	}

	public boolean existsReviewByMemberIdAndItemId(
		final Long memberId,
		final Long itemId
	) {
		return reviewRepository.existsReviewByMemberIdAndItemId(memberId, itemId);
	}

	public int countReviewByItemId(final Long itemId) {
		return reviewRepository.countReviewByItemId(itemId);
	}
}
