package com.programmers.bucketback.domains.vote.model;

import java.util.List;

import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.ItemBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoteSummaryBuilder {
	public static List<VoteSummary> buildMany(final List<VoteCursorSummary> voteSummaries) {
		return voteSummaries.stream()
			.map(voteCursorSummary -> {
				final Long item1Id = voteCursorSummary.item1Id();
				final Item item1 = ItemBuilder.build(item1Id);
				final Long item2Id = voteCursorSummary.item2Id();
				final Item item2 = ItemBuilder.build(item2Id);

				return VoteSummary.of(voteCursorSummary, item1, item2);
			})
			.toList();
	}
}
