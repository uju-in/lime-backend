package com.programmers.lime.domains.favorite.api.dto.response;

import java.util.List;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.favorite.application.dto.MemberItemFolderGetServiceResponse;
import com.programmers.lime.domains.item.model.MemberItemFolderCursorSummary;

public record MemberItemFolderGetByCursorResponse (

	String nextCursorId,

	int totalMemberItemFolderCount,

	int totalCount,

	List<MemberItemFolderCursorSummary> memberItemFolderCursorSummaries
) {

	public static MemberItemFolderGetByCursorResponse from(final MemberItemFolderGetServiceResponse serviceResponse) {
		CursorSummary<MemberItemFolderCursorSummary> cursorSummary = serviceResponse.memberItemFolderSummarySummary();

		return new MemberItemFolderGetByCursorResponse(
			cursorSummary.nextCursorId(),
			serviceResponse.totalMemberItemFolderCount(),
			cursorSummary.summaryCount(),
			cursorSummary.summaries()
		);
	}
}
