package com.programmers.lime.domains.review.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.review.domain.ReviewImage;
import com.programmers.lime.domains.review.repository.ReviewImageRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewImageAppender {

	private final ReviewImageRepository reviewImageRepository;

	public void append(
		final Long reviewId,
		final List<String> imageUrls
	) {
		List<ReviewImage> reviewImages = imageUrls.stream()
			.map(imageUrl -> new ReviewImage(reviewId, imageUrl))
			.toList();

		reviewImageRepository.saveAll(reviewImages);
	}
}
