package com.programmers.bucketback.domains.review.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.item.application.ItemReader;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.review.application.dto.ReviewContent;
import com.programmers.bucketback.domains.review.domain.Review;
import com.programmers.bucketback.domains.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewAppender {

	private final ItemReader itemReader;

	private final ReviewRepository reviewRepository;

	@Transactional
	public void append(
		final Long itemId,
		final Long memberId,
		final ReviewContent reviewContent
	) {
		Item item = itemReader.read(itemId);
		Review review = getReview(memberId, reviewContent, item);

		reviewRepository.save(review);
	}

	private Review getReview(
		final Long memberId,
		final ReviewContent reviewContent,
		final Item item
	) {
		return Review.builder()
			.item(item)
			.rating(reviewContent.rating())
			.content(reviewContent.content())
			.memberId(memberId)
			.build();
	}
}
