package com.programmers.lime.domains.favoriteItem.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.favoriteItem.application.dto.FolderGetByCursorServiceResponse;
import com.programmers.lime.domains.item.model.MemberItemFolderCursorSummary;

public record FolderGetByCursorResponse(
	String nextCursorId,
	int totalCount,
	List<MemberItemFolderCursorSummary> summaries
) {

	public static FolderGetByCursorResponse from(final FolderGetByCursorServiceResponse response) {
		return new FolderGetByCursorResponse(
			response.cursorSummary().nextCursorId(),
			response.folderTotalCount(),
			response.cursorSummary().summaries()
		);
	}
}
