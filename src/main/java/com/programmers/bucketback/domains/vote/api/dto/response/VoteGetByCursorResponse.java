package com.programmers.bucketback.domains.vote.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.vote.application.VoteCursorSummary;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVotesServiceResponse;

public record VoteGetByCursorResponse(
	String nextCursorId,
	List<VoteCursorSummary> voteCursorSummaries
) {
	public static VoteGetByCursorResponse from(final GetVotesServiceResponse serviceResponse) {
		return new VoteGetByCursorResponse(serviceResponse.nextCursorId(), serviceResponse.voteCursorSummaries());
	}
}
