package com.programmers.bucketback.domains.bucket.api.dto.response;

import java.util.List;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.bucket.model.BucketSummary;

public record BucketGetByCursorResponse(
	String nextCursorId,
	List<BucketSummary> buckets
) {
	public static BucketGetByCursorResponse from(final CursorSummary<BucketSummary> summary) {
		return new BucketGetByCursorResponse(
			summary.nextCursorId(),
			summary.summaries()
		);
	}
}
