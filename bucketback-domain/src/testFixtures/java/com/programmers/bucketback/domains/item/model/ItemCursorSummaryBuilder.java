package com.programmers.bucketback.domains.item.model;

import java.util.List;
import java.util.stream.LongStream;

public class ItemCursorSummaryBuilder {

	public static ItemCursorSummary build(final Long id) {
		return new ItemCursorSummary(
			"202301010000000000000" + id,
			ItemSummaryBuilder.build(id)
		);
	}

	public static List<ItemCursorSummary> buildMany() {
		return LongStream.range(1, 11)
			.mapToObj(ItemCursorSummaryBuilder::build)
			.toList();
	}
}
