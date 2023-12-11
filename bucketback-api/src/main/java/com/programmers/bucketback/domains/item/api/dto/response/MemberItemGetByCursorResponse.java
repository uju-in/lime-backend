package com.programmers.bucketback.domains.item.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.item.application.dto.MemberItemGetServiceResponse;
import com.programmers.bucketback.domains.item.model.MemberItemSummary;

public record MemberItemGetByCursorResponse(
	String nextCursorId,
	int totalCount,
	int totalMemberItemCount,
	List<MemberItemSummary> summaries
) {
	public static MemberItemGetByCursorResponse from(final MemberItemGetServiceResponse response) {
		return new MemberItemGetByCursorResponse(
			response.cursorSummary().nextCursorId(),
			response.cursorSummary().summaryCount(),
			response.totalMemberItemCount(),
			response.cursorSummary().summaries()
		);
	}
}
