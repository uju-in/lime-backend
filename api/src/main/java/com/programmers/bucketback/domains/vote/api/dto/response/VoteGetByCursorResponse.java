package com.programmers.bucketback.domains.vote.api.dto.response;

import com.programmers.bucketback.domains.vote.model.response.VoteCursorSummary;
import com.programmers.bucketback.domains.vote.model.response.VotesGetServiceResponse;

import java.util.List;

public record VoteGetByCursorResponse(
	String nextCursorId,
	List<VoteCursorSummary> votes
) {
	public static VoteGetByCursorResponse from(final VotesGetServiceResponse serviceResponse) {
		return new VoteGetByCursorResponse(serviceResponse.nextCursorId(), serviceResponse.votes());
	}
}
