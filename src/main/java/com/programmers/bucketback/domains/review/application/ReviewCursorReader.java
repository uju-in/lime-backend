package com.programmers.bucketback.domains.review.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.review.application.dto.GetReviewByCursorServiceResponse;
import com.programmers.bucketback.domains.review.application.vo.ReviewCursorSummary;
import com.programmers.bucketback.domains.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewCursorReader {

	private final ReviewRepository reviewRepository;

	private static String getNextCursorId(final List<ReviewCursorSummary> reviewCursorSummaries) {
		int reviewCursorSummariesSize = reviewCursorSummaries.size();
		if (reviewCursorSummariesSize == 0) {
			return null;
		}

		ReviewCursorSummary lastElement = reviewCursorSummaries.get(reviewCursorSummariesSize - 1);

		return lastElement.cursorId();
	}

	public GetReviewByCursorServiceResponse readByCursor(
		final Long itemId,
		final CursorPageParameters parameters
	) {
		int pageSize = parameters.size() == 0 ? 20 : parameters.size();

		List<ReviewCursorSummary> reviewCursorSummaries = reviewRepository.findAllByCursor(
			itemId,
			parameters.cursorId(),
			pageSize
		);

		String nextCursorId = getNextCursorId(reviewCursorSummaries);

		Long reviewCount = reviewRepository.getReviewCount(itemId);

		return new GetReviewByCursorServiceResponse(
			reviewCount,
			nextCursorId,
			reviewCursorSummaries
		);
	}
}
