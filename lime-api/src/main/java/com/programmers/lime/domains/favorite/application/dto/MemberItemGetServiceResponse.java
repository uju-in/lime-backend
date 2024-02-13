package com.programmers.lime.domains.favorite.application.dto;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.item.model.MemberItemSummary;

public record MemberItemGetServiceResponse(
	CursorSummary<MemberItemSummary> cursorSummary,
	int totalMemberItemCount
) {
}
