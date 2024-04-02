package com.programmers.lime.domains.review.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.review.repository.ReviewLikeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewLikeReader {

	private final ReviewLikeRepository reviewLikeRepository;

	public boolean alreadyLiked(
		final Long memberId,
		final Long reviewId
	) {
		return reviewLikeRepository.existsByMemberIdAndReviewId(memberId, reviewId);
	}
}
