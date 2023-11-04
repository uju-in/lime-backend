package com.programmers.bucketback.domains.item.application.vo;

public record ItemCursorSummary(
	String cursorId,
	ItemSummary itemInfo
) {
	public static ItemCursorSummary of(
		final String cursorId,
		final ItemSummary itemSummary
	) {
		return new ItemCursorSummary(
			cursorId,
			itemSummary
		);
	}
}
