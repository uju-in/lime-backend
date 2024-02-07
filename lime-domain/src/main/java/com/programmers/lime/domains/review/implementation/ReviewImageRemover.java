package com.programmers.lime.domains.review.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.review.repository.ReviewImageRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewImageRemover {

	private final ReviewImageRepository reviewImageRepository;

	public void removeReviewImagesImageUrls(final List<String> reviewImageUrls) {
		if(reviewImageUrls == null || reviewImageUrls.isEmpty()) {
			return;
		}

		reviewImageRepository.deleteByImageUrlIn(reviewImageUrls);
	}
}
