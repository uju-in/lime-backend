package com.programmers.bucketback.domains.vote.application.dto.response;

public record VoteSummary(
	VoteInfo voteInfo,
	Long item1Id,
	Long item2Id,
	String cursorId
) {
}
