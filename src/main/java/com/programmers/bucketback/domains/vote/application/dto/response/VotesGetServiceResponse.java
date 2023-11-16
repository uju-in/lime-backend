package com.programmers.bucketback.domains.vote.application.dto.response;

import java.util.List;

public record VotesGetServiceResponse(
	String nextCursorId,
	List<VoteCursorSummary> votes
) {
}
