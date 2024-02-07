package com.programmers.lime.domains.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.review.domain.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
	void deleteReviewImageByReviewId(Long reviewId);
}
