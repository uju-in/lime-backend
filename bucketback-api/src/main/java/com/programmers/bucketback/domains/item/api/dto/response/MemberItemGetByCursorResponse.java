package com.programmers.bucketback.domains.item.api.dto.response;

import java.util.List;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.item.model.MemberItemSummary;

public record MemberItemGetByCursorResponse(
	String nextCursorId,
	int totalCount,
	List<MemberItemSummary> summaries
) {
	public static MemberItemGetByCursorResponse from(final CursorSummary<MemberItemSummary> summary) {
		return new MemberItemGetByCursorResponse(
			summary.nextCursorId(),
			summary.summaryCount(),
			summary.summaries()
		);
	}
}
