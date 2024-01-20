package com.programmers.lime.domains.item.model;

import com.programmers.lime.common.cursor.CursorIdParser;

public record MemberItemFolderCursorSummary(
	String cursorId,
	MemberItemFolderSummary memberItemFolderSummary
) implements CursorIdParser {
	@Override
	public String cursorId() {
		return cursorId;
	}
}
