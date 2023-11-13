package com.programmers.bucketback.domains.vote.application.dto.response;

import com.programmers.bucketback.domains.item.application.vo.ItemInfo;
import com.programmers.bucketback.domains.item.domain.Item;

import lombok.Builder;

@Builder
public record VoteCursorSummary(
	VoteInfo voteInfo,
	ItemInfo item1Info,
	ItemInfo item2Info,
	String cursorId
) {
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
