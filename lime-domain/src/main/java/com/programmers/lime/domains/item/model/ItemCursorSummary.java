package com.programmers.lime.domains.item.model;

import com.programmers.lime.common.cursor.CursorIdParser;

public record ItemCursorSummary(
	String cursorId,
	ItemSummary itemSummary
) implements CursorIdParser {

	@Override
	public String cursorId() {
		return cursorId;
	}
}
