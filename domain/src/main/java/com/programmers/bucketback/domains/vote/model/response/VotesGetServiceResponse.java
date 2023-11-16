package com.programmers.bucketback.domains.vote.model.response;

import java.util.List;

public record VotesGetServiceResponse(
	String nextCursorId,
	List<VoteCursorSummary> votes
) {
}
