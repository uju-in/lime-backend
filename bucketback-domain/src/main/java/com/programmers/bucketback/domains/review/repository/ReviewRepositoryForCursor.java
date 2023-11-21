package com.programmers.bucketback.domains.review.repository;

import java.util.List;

import com.programmers.bucketback.domains.review.model.ReviewCursorSummary;

public interface ReviewRepositoryForCursor {
	List<ReviewCursorSummary> findAllByCursor(
		Long itemId,
		Long memberId,
		String cursorId,
		int pageSize
	);

	Long getReviewCount(Long itemId);
}
