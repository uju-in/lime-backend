package com.programmers.lime.domains.review.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewStatistics {

	private final ReviewRepository reviewRepository;

	public int getReviewCount(final Long itemId) {
		return reviewRepository.getReviewCount(itemId);
	}

	public Double getReviewAvgByItemId(final Long itemId) {
		return reviewRepository.getAvgRatingByReviewId(itemId);
	}
}
