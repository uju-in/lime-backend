package com.programmers.bucketback.domains.item.model;

import java.time.LocalDateTime;

import com.programmers.bucketback.common.cursor.CursorIdParser;

public record MemberItemSummary(
	String cursorId,
	LocalDateTime createdAt,
	ItemInfo itemInfo
) implements CursorIdParser {
}
