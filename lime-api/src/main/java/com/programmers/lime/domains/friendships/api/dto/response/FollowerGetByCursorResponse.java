package com.programmers.lime.domains.friendships.api.dto.response;

import java.util.List;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.friendships.model.FollowerSummary;

public record FollowerGetByCursorResponse(
	String nextCursorId,
	Integer totalCount,
	List<FollowerSummary> followers
) {
	public static FollowerGetByCursorResponse from(final CursorSummary<FollowerSummary> cursorSummary) {
		return new FollowerGetByCursorResponse(
			cursorSummary.nextCursorId(),
			cursorSummary.summaryCount(),
			cursorSummary.summaries()
		);
	}
}
