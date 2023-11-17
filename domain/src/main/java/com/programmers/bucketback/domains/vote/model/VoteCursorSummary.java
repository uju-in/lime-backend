package com.programmers.bucketback.domains.vote.model;

import com.programmers.bucketback.domains.vote.model.response.VoteInfo;

public record VoteCursorSummary(
	VoteInfo voteInfo,
	Long item1Id,
	Long item2Id,
	String cursorId
) {
}
