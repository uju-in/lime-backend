package com.programmers.bucketback.domains.vote.api.dto.response;

import java.util.List;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.vote.model.VoteCursorSummary;

public record VoteGetByCursorResponse(
	String nextCursorId,
	List<VoteCursorSummary> votes
) {
	public static VoteGetByCursorResponse from(final CursorSummary<VoteCursorSummary> cursorSummary) {
		return new VoteGetByCursorResponse(cursorSummary.nextCursorId(), cursorSummary.summaries());
	}
}
