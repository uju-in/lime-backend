package com.programmers.lime.domains.vote.model;

public record VoteCursorSummary(
	VoteInfo voteInfo,
	Long item1Id,
	Long item2Id,
	String cursorId
) {
}
