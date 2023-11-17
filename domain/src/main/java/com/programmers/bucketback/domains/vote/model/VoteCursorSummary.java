package com.programmers.bucketback.domains.vote.model;

import com.programmers.bucketback.common.cursor.CursorIdParser;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.model.ItemInfo;
import com.programmers.bucketback.domains.vote.model.response.VoteInfo;

import lombok.Builder;

@Builder
public record VoteCursorSummary(
	VoteInfo voteInfo,
	ItemInfo item1Info,
	ItemInfo item2Info,
	String cursorId
) implements CursorIdParser {
	public static VoteCursorSummary of(
		final VoteSummary voteSummary,
		final Item item1,
		final Item item2
	) {
		return VoteCursorSummary.builder()
			.voteInfo(voteSummary.voteInfo())
			.item1Info(ItemInfo.from(item1))
			.item2Info(ItemInfo.from(item2))
			.cursorId(voteSummary.cursorId())
			.build();
	}
}
