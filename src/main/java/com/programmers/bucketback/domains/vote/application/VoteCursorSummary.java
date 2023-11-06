package com.programmers.bucketback.domains.vote.application;

import lombok.Builder;

@Builder
public record VoteCursorSummary(
	VoteInfo voteInfo,
	OptionItem option1Item,
	OptionItem option2Item,
	String cursorId
) {
}
