package com.programmers.bucketback.domains.bucket.application.dto.response;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.bucket.model.BucketMemberItemSummary;

public record BucketGetMemberItemServiceResponse(
	CursorSummary<BucketMemberItemSummary> cursorSummary,
	int totalMemberItemCount
) {
}
