package com.programmers.bucketback.domains.vote.application;

import com.programmers.bucketback.domains.item.domain.Item;

import lombok.Builder;

@Builder
public record VoteCursorSummary(
	VoteInfo voteInfo,
	OptionItem option1Item,
	OptionItem option2Item,
	String cursorId
) {
	public static VoteCursorSummary of(
		final VoteSummary voteSummary,
		final Item item1,
		final Item item2
	) {
		return VoteCursorSummary.builder()
			.voteInfo(voteSummary.voteInfo())
			.option1Item(OptionItem.from(item1))
			.option2Item(OptionItem.from(item2))
			.cursorId(voteSummary.cursorId())
			.build();
	}
}
