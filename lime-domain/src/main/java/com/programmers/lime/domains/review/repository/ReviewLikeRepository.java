package com.programmers.lime.domains.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.review.domain.Review;
import com.programmers.lime.domains.review.domain.ReviewLike;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

	void deleteReviewLikeByMemberIdAndReview(
		final Long memberId,
		final Review review
	);

	boolean existsByMemberIdAndReviewId(
		final Long memberId,
		final Long reviewId
	);

	void deleteReviewLikeByReviewId(Long reviewId);
}
