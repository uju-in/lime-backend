package com.programmers.lime.domains.bucket.application.dto.response;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.bucket.model.BucketMemberItemSummary;

public record BucketGetMemberItemServiceResponse(
	CursorSummary<BucketMemberItemSummary> cursorSummary,
	int totalMemberItemCount
) {
}
