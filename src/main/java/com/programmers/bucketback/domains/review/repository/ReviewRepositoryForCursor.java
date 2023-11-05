package com.programmers.bucketback.domains.review.repository;

import java.util.List;

import com.programmers.bucketback.domains.review.application.vo.ReviewSummary;

public interface ReviewRepositoryForCursor {
	List<ReviewSummary> findAllByCursor(
		Long itemId,
		String cursorId,
		int pageSize
	);

	Long getReviewCount(Long itemId);
}
