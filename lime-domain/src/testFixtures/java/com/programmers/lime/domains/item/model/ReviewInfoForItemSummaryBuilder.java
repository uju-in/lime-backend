package com.programmers.lime.domains.item.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewInfoForItemSummaryBuilder {

	public static ReviewInfoForItemSummary build(Long itemId, Long reviewCount) {
		return new ReviewInfoForItemSummary(
			itemId, reviewCount
		);
	}

	public static List<ReviewInfoForItemSummary> buildMany(List<Long> ids) {
		return ids.stream().map(
			id -> build(id, id)
		).toList();
	}
}
