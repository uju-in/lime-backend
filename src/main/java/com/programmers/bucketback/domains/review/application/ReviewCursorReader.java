package com.programmers.bucketback.domains.review.application;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.review.application.dto.GetReviewByCursorServiceResponse;
import com.programmers.bucketback.domains.review.application.vo.ReviewCursorSummary;
import com.programmers.bucketback.domains.review.application.vo.ReviewSummary;
import com.programmers.bucketback.domains.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewCursorReader {

	private final ReviewRepository reviewRepository;

	public GetReviewByCursorServiceResponse readByCursor(
		final Long itemId,
		final CursorPageParameters parameters
	) {
		int pageSize = parameters.size() == 0 ? 20 : parameters.size();

		List<ReviewSummary> reviewSummaries = reviewRepository.findAllByCursor(
			itemId,
			parameters.cursorId(),
			pageSize
		);

		List<String> cursorIds = reviewSummaries.stream()
			.map(this::generateCursorId)
			.toList();

		String nextCursorId = cursorIds.size() == 0 ? null : cursorIds.get(cursorIds.size() - 1);

		List<ReviewCursorSummary> reviewCursorSummaries = getReviewCursorSummaries(reviewSummaries, cursorIds);

		Long reviewCount = reviewRepository.getReviewCount(itemId);

		return new GetReviewByCursorServiceResponse(
			reviewCount,
			nextCursorId,
			reviewCursorSummaries
		);
	}

	private List<ReviewCursorSummary> getReviewCursorSummaries(
		final List<ReviewSummary> reviewSummaries,
		final List<String> cursorIds
	) {
		return IntStream.range(0, reviewSummaries.size())
			.mapToObj(idx -> {
				String cursorId = cursorIds.get(idx);
				ReviewSummary reviewSummary = reviewSummaries.get(idx);

				return ReviewCursorSummary.of(cursorId, reviewSummary);
			}).toList();
	}

	private String generateCursorId(final ReviewSummary reviewSummary) {
		return reviewSummary.createdAt().toString()
			.replace("T", "")
			.replace("-", "")
			.replace(":", "")
			.replace(".", "")
			+ String.format("%08d", reviewSummary.reviewId());
	}
}
