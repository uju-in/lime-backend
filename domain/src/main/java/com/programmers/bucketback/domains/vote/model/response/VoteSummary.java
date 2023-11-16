package com.programmers.bucketback.domains.vote.model.response;

public record VoteSummary(
	VoteInfo voteInfo,
	Long item1Id,
	Long item2Id,
	String cursorId
) {
}
