package com.programmers.bucketback.domains.bucket.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketMemberItemCursorSummary;

public record BucketGetMemberItemResponse(
	String nextCursorId,
	int summaryCount,
	List<BucketMemberItemCursorSummary> bucketMemberItemCursorSummaries
) {
}
