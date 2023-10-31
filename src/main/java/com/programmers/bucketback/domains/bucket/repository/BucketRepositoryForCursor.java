package com.programmers.bucketback.domains.bucket.repository;

import org.springframework.data.domain.Pageable;

public interface BucketRepositoryForCursor {
	List<BucketSummary> findAllByCursor(
		String cursorId,
		Pageable pageable
	);
}
