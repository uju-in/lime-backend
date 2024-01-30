package com.programmers.lime.domains.review.repository;

import java.util.List;

import com.programmers.lime.domains.review.model.MemberInfoWithReviewId;
import com.programmers.lime.domains.review.model.ReviewCursorIdInfo;
import com.programmers.lime.domains.review.model.ReviewImageInfo;
import com.programmers.lime.domains.review.model.ReviewInfo;
import com.programmers.lime.domains.review.model.ReviewSortCondition;

public interface ReviewRepositoryForCursor {
	List<ReviewCursorIdInfo> findAllByCursor(
		Long itemId,
		String cursorId,
		int pageSize,
		ReviewSortCondition reviewSortCondition
	);

	List<MemberInfoWithReviewId> getMemberInfos(List<Long> reviewIds);

	List<ReviewImageInfo> getReviewImageInfos(List<Long> reviewIds);

	List<ReviewInfo> getReviewInfo(List<Long> reviewIds);

	int getReviewCount(Long itemId);
}
