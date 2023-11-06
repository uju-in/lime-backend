package com.programmers.bucketback.domains.vote.application;

import com.programmers.bucketback.domains.vote.domain.Vote;

public record VoteSummary(
	Vote vote,
	String cursorId
) {
}
