package com.programmers.lime.domains.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.review.domain.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

	boolean existsReviewImagesByReviewIdAndImageUrl(Long ReviewId, String imageUrl);

	void deleteByImageUrlIn(List<String> reviewImageUrls);
}
