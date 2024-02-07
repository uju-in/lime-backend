package com.programmers.lime.domains.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.review.domain.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

	boolean existsReviewImagesByReviewIdAndImageUrl(final Long ReviewId, final String imageUrl);

	void deleteByImageUrlIn(final List<String> reviewImageUrls);
}
