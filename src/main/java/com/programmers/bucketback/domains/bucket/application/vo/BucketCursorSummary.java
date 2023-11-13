package com.programmers.bucketback.domains.bucket.application.vo;

import java.util.List;

import com.programmers.bucketback.domains.bucket.api.dto.response.BucketGetByCursorResponse;

public record BucketCursorSummary(
	String nextCursorId,
	int summaryCount,
	List<BucketSummary> bucketSummaries
) {
	public BucketGetByCursorResponse toBucketGetByCursorResponse() {
		return new BucketGetByCursorResponse(
			nextCursorId(),
			bucketSummaries()
		);
	}
}
