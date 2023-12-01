package com.programmers.bucketback.domains.vote.application.dto.response;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.vote.model.VoteSummary;

public record VoteGetByKeywordServiceResponse(
	CursorSummary<VoteSummary> voteSummary,
	long totalVoteCount
) {
}
