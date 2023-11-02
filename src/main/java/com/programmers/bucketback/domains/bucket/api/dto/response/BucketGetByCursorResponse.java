package com.programmers.bucketback.domains.bucket.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketCursorSummary;

public record BucketGetByCursorResponse(
	String nextCursorId,
	List<BucketCursorSummary> bucketCursorSummaries
) {
}
