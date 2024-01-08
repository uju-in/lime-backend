package com.programmers.lime.domains.vote.application.dto.response;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.vote.model.VoteSummary;

public record VoteGetByKeywordServiceResponse(
	CursorSummary<VoteSummary> voteSummary,
	long totalVoteCount
) {
}
