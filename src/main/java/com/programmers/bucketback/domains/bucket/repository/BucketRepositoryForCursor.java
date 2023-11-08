package com.programmers.bucketback.domains.bucket.repository;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketSummary;
import com.programmers.bucketback.domains.common.Hobby;

public interface BucketRepositoryForCursor {
	List<BucketSummary> findAllByCursor(
		final Long memberId,
		final Hobby hobby,
		final String cursorId,
		final int pageSize
	);
}
