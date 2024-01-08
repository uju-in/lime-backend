package com.programmers.lime.domains.item.model;

import java.time.LocalDateTime;

import com.programmers.lime.common.cursor.CursorIdParser;

public record MemberItemSummary(
	String cursorId,
	LocalDateTime createdAt,
	ItemInfo itemInfo
) implements CursorIdParser {
}
