package com.programmers.bucketback.domains.vote.application.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.vote.model.VoteCursorSummary;

public record VotesGetServiceResponse(
	String nextCursorId,
	List<VoteCursorSummary> votes
) {
}
