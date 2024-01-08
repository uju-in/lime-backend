package com.programmers.lime.domains.review.repository;

import java.util.List;

import com.programmers.lime.domains.review.model.ReviewCursorSummary;

public interface ReviewRepositoryForCursor {
	List<ReviewCursorSummary> findAllByCursor(
		Long itemId,
		Long memberId,
		String cursorId,
		int pageSize
	);

	int getReviewCount(Long itemId);
}
