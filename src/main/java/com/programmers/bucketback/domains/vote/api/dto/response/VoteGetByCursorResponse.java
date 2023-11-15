package com.programmers.bucketback.domains.vote.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.vote.application.dto.response.GetVotesServiceResponse;
import com.programmers.bucketback.domains.vote.application.dto.response.VoteCursorSummary;

public record VoteGetByCursorResponse(
	String nextCursorId,
	List<VoteCursorSummary> votes
) {
	public static VoteGetByCursorResponse from(final GetVotesServiceResponse serviceResponse) {
		return new VoteGetByCursorResponse(serviceResponse.nextCursorId(), serviceResponse.votes());
	}
}
