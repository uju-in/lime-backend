package com.programmers.bucketback.domains.bucket.api.dto.response;

import com.programmers.bucketback.domains.bucket.application.vo.BucketMemberItemCursorSummary;

public record BucketGetMemberItemResponse(
	BucketMemberItemCursorSummary bucketMemberItemCursorSummary
) {
}
