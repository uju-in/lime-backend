package com.programmers.bucketback.domains.review.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewStatistics {

	private final ReviewRepository reviewRepository;
	
	public double getReviewAvgByItemId(Long itemId) {
		return reviewRepository.getAvgRatingByReviewId(itemId);
	}
}
