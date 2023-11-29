package com.programmers.bucketback.domains.bucket.application.dto.response;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.bucket.model.BucketSummary;

public record BucketGetCursorServiceResponse(
	CursorSummary<BucketSummary> cursorSummary,
	int totalBucketCount
) {
}
