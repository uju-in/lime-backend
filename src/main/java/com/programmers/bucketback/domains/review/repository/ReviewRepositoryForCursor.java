package com.programmers.bucketback.domains.review.repository;

import java.util.List;

import com.programmers.bucketback.domains.review.application.vo.ReviewCursorSummary;

public interface ReviewRepositoryForCursor {
	List<ReviewCursorSummary> findAllByCursor(
		Long itemId,
		String cursorId,
		int pageSize
	);

	Long getReviewCount(Long itemId);
}
