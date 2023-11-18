package com.programmers.bucketback.domains.vote.model;

public record VoteCursorSummary(
	VoteInfo voteInfo,
	Long item1Id,
	Long item2Id,
	String cursorId
) {
}
