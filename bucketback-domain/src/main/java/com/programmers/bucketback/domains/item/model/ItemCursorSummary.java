package com.programmers.bucketback.domains.item.model;

import com.programmers.bucketback.common.cursor.CursorIdParser;

public record ItemCursorSummary(
	String cursorId,
	ItemSummary itemSummary
) implements CursorIdParser {

	@Override
	public String cursorId() {
		return cursorId;
	}
}
