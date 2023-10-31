package com.programmers.bucketback.domains.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
