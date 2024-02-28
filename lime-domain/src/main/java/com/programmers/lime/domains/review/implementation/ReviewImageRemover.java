package com.programmers.lime.domains.review.implementation;

import java.util.List;
import org.springframework.stereotype.Component;

import com.programmers.lime.domains.review.repository.ReviewImageRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewImageRemover {

	private final ReviewImageRepository reviewImageRepository;

	public void deleteByReviewId(
		final Long reviewId
	) {
		reviewImageRepository.deleteReviewImageByReviewId(reviewId);
  }
  
	public void removeReviewImagesImageUrls(final List<String> reviewImageUrls) {
		if(reviewImageUrls == null || reviewImageUrls.isEmpty()) {
			return;
		}

		reviewImageRepository.deleteByImageUrlIn(reviewImageUrls);
	}
}
