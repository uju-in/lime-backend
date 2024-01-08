package com.programmers.lime.domains.review;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.common.model.ItemIdRegistryBuilder;
import com.programmers.lime.domains.review.domain.Review;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewBuilder {

	public static List<Review> buildMany() {
		ItemIdRegistry itemIdRegistry = ItemIdRegistryBuilder.build();

		return IntStream.range(0, itemIdRegistry.itemIds().size())
			.mapToObj(i -> build(itemIdRegistry.itemIds().get(i), Long.valueOf(i + 1)))
			.toList();
	}

	public static Review build(
		final Long itemId,
		final Long reviewId
	) {
		Review review = Review.builder()
			.memberId(1L)
			.itemId(itemId)
			.content("리뷰 내용")
			.rating(0)
			.build();

		setReviewId(review, reviewId);

		return review;
	}

	private static void setReviewId(
		final Review review,
		final Long reviewId
	) {
		ReflectionTestUtils.setField(
			review,
			"id",
			reviewId
		);
	}
}
