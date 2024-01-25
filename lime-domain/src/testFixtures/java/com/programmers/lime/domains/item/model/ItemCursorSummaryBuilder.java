package com.programmers.lime.domains.item.model;

import java.util.List;

import com.programmers.lime.common.cursor.CursorIdBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemCursorSummaryBuilder {

	public static ItemCursorSummary build(final Long id) {
		return new ItemCursorSummary(
			CursorIdBuilder.build(id),
			ItemSummaryBuilder.build(id)
		);
	}

	public static List<ItemCursorSummary> buildMany(List<Long> ids) {
		return ids.stream().map(
			ItemCursorSummaryBuilder::build
		).toList();
	}
}
