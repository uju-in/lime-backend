package com.programmers.bucketback.domains.bucket.repository;

import java.util.List;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.bucket.model.BucketSummary;

public interface BucketRepositoryForCursor {
	List<BucketSummary> findAllByCursor(
		final Long memberId,
		final Hobby hobby,
		final String cursorId,
		final int pageSize
	);
}
