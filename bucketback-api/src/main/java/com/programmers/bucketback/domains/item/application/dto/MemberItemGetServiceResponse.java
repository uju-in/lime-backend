package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.item.model.MemberItemSummary;

public record MemberItemGetServiceResponse(
	CursorSummary<MemberItemSummary> cursorSummary,
	int totalMemberItemCount
) {
}
