package com.programmers.lime.domains.bucket.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.bucket.application.dto.response.BucketGetCursorServiceResponse;
import com.programmers.lime.domains.bucket.model.BucketSummary;

public record BucketGetByCursorResponse(
	String nextCursorId,
	int totalBucketCount,
	List<BucketSummary> buckets
) {
	public static BucketGetByCursorResponse from(final BucketGetCursorServiceResponse response) {
		return new BucketGetByCursorResponse(
			response.cursorSummary().nextCursorId(),
			response.totalBucketCount(),
			response.cursorSummary().summaries()
		);
	}
}
