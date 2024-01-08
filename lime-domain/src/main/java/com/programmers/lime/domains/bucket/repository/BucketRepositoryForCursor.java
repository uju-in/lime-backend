package com.programmers.lime.domains.bucket.repository;

import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.bucket.model.BucketSummary;

public interface BucketRepositoryForCursor {
	List<BucketSummary> findAllByCursor(
		final Long memberId,
		final Hobby hobby,
		final String cursorId,
		final int pageSize
	);
}
