package com.programmers.bucketback.domains.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.programmers.bucketback.domains.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryForCursor {

	@Query("SELECT ROUND(AVG(r.rating), 1) FROM Review r WHERE r.itemId = :itemId")
	Double getAvgRatingByReviewId(@Param("itemId") Long itemId);

	List<Review> findByMemberId(Long memberId);
}
