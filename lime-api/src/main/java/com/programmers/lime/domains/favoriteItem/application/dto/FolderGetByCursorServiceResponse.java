package com.programmers.lime.domains.favoriteItem.application.dto;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.item.model.MemberItemFolderCursorSummary;

public record FolderGetByCursorServiceResponse(
	int folderTotalCount,
	CursorSummary<MemberItemFolderCursorSummary> cursorSummary
) {
}
