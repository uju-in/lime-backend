package com.programmers.bucketback.domains.bucket.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketCursorSummary;
import com.programmers.bucketback.domains.bucket.application.vo.BucketSummary;

public record BucketGetByCursorResponse(
	String nextCursorId,
	List<BucketSummary> buckets
) {
	public static BucketGetByCursorResponse from(final BucketCursorSummary summary) {
		return new BucketGetByCursorResponse(
			summary.nextCursorId(),
			summary.bucketSummaries()
		);
	}
}
