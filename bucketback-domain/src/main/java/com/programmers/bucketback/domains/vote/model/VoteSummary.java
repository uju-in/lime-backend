package com.programmers.bucketback.domains.vote.model;

import com.programmers.bucketback.common.cursor.CursorIdParser;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.model.ItemInfo;

import lombok.Builder;

@Builder
public record VoteSummary(
	VoteInfo voteInfo,
	ItemInfo item1Info,
	ItemInfo item2Info,
	String cursorId
) implements CursorIdParser {
	public static VoteSummary of(
		final VoteCursorSummary voteCursorSummary,
		final Item item1,
		final Item item2
	) {
		return VoteSummary.builder()
			.voteInfo(voteCursorSummary.voteInfo())
			.item1Info(ItemInfo.from(item1))
			.item2Info(ItemInfo.from(item2))
			.cursorId(voteCursorSummary.cursorId())
			.build();
	}
}
