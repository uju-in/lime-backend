package com.programmers.bucketback.domains.bucket.repository;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketSummary;
import com.programmers.bucketback.domains.common.Hobby;

public interface BucketRepositoryForCursor {
	List<BucketSummary> findAllByCursor(
		Long memberId,
		Hobby hobby,
		String cursorId,
		int pageable
	);
}
