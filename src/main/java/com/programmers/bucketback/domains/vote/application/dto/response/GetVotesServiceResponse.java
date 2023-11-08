package com.programmers.bucketback.domains.vote.application.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.vote.application.VoteCursorSummary;

public record GetVotesServiceResponse(
	String nextCursorId,
	List<VoteCursorSummary> voteCursorSummaries
) {
}
