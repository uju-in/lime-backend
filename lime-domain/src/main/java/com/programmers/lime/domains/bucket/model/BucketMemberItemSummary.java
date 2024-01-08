package com.programmers.lime.domains.bucket.model;

import java.time.LocalDateTime;

import com.programmers.lime.common.cursor.CursorIdParser;
import com.programmers.lime.domains.item.model.ItemInfo;

public record BucketMemberItemSummary(
	String cursorId,
	boolean isSelected,
	LocalDateTime createdAt,
	ItemInfo itemInfo
) implements CursorIdParser {
}
