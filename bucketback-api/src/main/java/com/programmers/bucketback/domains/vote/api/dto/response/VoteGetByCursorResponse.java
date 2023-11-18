package com.programmers.bucketback.domains.vote.api.dto.response;

import java.util.List;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.vote.model.VoteSummary;

public record VoteGetByCursorResponse(
	String nextCursorId,
	List<VoteSummary> votes
) {
	public static VoteGetByCursorResponse from(final CursorSummary<VoteSummary> cursorSummary) {
		return new VoteGetByCursorResponse(cursorSummary.nextCursorId(), cursorSummary.summaries());
	}
}
