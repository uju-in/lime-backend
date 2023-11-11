package com.programmers.bucketback.domains.vote.application.dto.response;

import java.util.List;

public record GetVotesServiceResponse(
	String nextCursorId,
	List<VoteCursorSummary> voteCursorSummaries
) {
}
