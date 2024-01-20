package com.programmers.lime.domains.item.application.dto;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.item.model.MemberItemFolderCursorSummary;
import com.programmers.lime.domains.item.model.MemberItemFolderSummary;

public record MemberItemFolderGetServiceResponse(

	CursorSummary<MemberItemFolderCursorSummary> memberItemFolderSummarySummary,
	int totalMemberItemFolderCount

	) {
}
