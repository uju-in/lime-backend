package com.programmers.lime.domains.bucket.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.bucket.application.dto.response.BucketGetMemberItemServiceResponse;
import com.programmers.lime.domains.bucket.model.BucketMemberItemSummary;

public record BucketGetMemberItemResponse(
	String nextCursorId,
	int totalCount,
	int totalMemberItemCount,
	List<BucketMemberItemSummary> memberItems
) {
	public static BucketGetMemberItemResponse from(final BucketGetMemberItemServiceResponse response) {
		return new BucketGetMemberItemResponse(
			response.cursorSummary().nextCursorId(),
			response.cursorSummary().summaryCount(),
			response.totalMemberItemCount(),
			response.cursorSummary().summaries()
		);
	}
}
