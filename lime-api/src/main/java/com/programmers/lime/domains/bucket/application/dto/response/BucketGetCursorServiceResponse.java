package com.programmers.lime.domains.bucket.application.dto.response;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.bucket.model.BucketSummary;

public record BucketGetCursorServiceResponse(
	CursorSummary<BucketSummary> cursorSummary,
	int totalBucketCount
) {
}
