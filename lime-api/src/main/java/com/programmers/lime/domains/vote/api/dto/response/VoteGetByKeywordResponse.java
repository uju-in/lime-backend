package com.programmers.lime.domains.vote.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.vote.application.dto.response.VoteGetByKeywordServiceResponse;
import com.programmers.lime.domains.vote.model.VoteSummary;

public record VoteGetByKeywordResponse(
	String nextCursorId,
	int totalCount,
	long totalVoteCount,
	List<VoteSummary> votes
) {
	public static VoteGetByKeywordResponse from(final VoteGetByKeywordServiceResponse response) {
		return new VoteGetByKeywordResponse(
			response.voteSummary().nextCursorId(),
			response.voteSummary().summaryCount(),
			response.totalVoteCount(),
			response.voteSummary().summaries()
		);
	}
}
