package com.programmers.lime.domains.vote.api.dto.response;

import java.util.List;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.vote.model.VoteSummary;

public record VoteGetByCursorResponse(
	String nextCursorId,
	Integer totalCount,
	List<VoteSummary> votes
) {
	public static VoteGetByCursorResponse from(final CursorSummary<VoteSummary> cursorSummary) {
		return new VoteGetByCursorResponse(
			cursorSummary.nextCursorId(),
			cursorSummary.summaryCount(),
			cursorSummary.summaries()
		);
	}
}
