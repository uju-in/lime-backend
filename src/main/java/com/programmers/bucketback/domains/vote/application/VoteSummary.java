package com.programmers.bucketback.domains.vote.application;

public record VoteSummary(
	VoteInfo voteInfo,
	Long option1ItemId,
	Long option2ItemId,
	String cursorId
) {
}
