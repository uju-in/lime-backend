package com.programmers.lime.domains.review.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.review.repository.ReviewImageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewImageReader {

	private final ReviewImageRepository reviewImageRepository;

	public boolean existsReviewImagesByReviewIdAndImageUrls(final Long reviewId, final List<String> reviewImageUrls) {
		return reviewImageUrls.stream()
			.map(String::trim)
			.noneMatch(
				reviewImageUrl ->
					reviewImageRepository.existsReviewImagesByReviewIdAndImageUrl(reviewId, reviewImageUrl)
			);
	}
}
