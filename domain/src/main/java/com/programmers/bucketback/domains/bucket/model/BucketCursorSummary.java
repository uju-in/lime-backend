package com.programmers.bucketback.domains.bucket.model;

import java.util.List;

public record BucketCursorSummary(
	String nextCursorId,
	int summaryCount,
	List<BucketSummary> bucketSummaries
) {
}
