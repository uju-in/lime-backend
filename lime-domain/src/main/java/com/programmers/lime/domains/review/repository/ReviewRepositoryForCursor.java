package com.programmers.lime.domains.review.repository;

import java.util.List;

import com.programmers.lime.domains.review.model.MemberInfoWithReviewId;
import com.programmers.lime.domains.review.model.ReviewCursorIdInfo;
import com.programmers.lime.domains.review.model.ReviewSortCondition;
import com.programmers.lime.domains.review.model.ReviewSummary;

public interface ReviewRepositoryForCursor {
	List<ReviewCursorIdInfo> findAllByCursor(
		Long itemId,
		String cursorId,
		int pageSize,
		ReviewSortCondition reviewSortCondition
	);

	List<MemberInfoWithReviewId> getMemberInfos(List<Long> reviewIds);

	List<ReviewSummary> getReviewSummaries(List<Long> reviewIds);

	int getReviewCount(Long itemId);
}
