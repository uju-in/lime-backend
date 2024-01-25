package com.programmers.lime.domains.review.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.review.domain.Review;
import com.programmers.lime.domains.review.domain.ReviewImage;
import com.programmers.lime.domains.review.repository.ReviewImageRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewImageAppender {

	private final ReviewImageRepository reviewImageRepository;

	private final ReviewReader reviewReader;

	public void append(
		final Long reviewId,
		final List<String> imageUrls
	) {
		Review review = reviewReader.read(reviewId);

		List<ReviewImage> reviewImages = imageUrls.stream()
			.map(imageUrl -> new ReviewImage(review, imageUrl))
			.toList();

		reviewImageRepository.saveAll(reviewImages);
	}
}
