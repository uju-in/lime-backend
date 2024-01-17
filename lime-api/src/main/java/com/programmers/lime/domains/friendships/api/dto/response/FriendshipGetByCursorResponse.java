package com.programmers.lime.domains.friendships.api.dto.response;

import java.util.List;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.friendships.model.FriendshipSummary;

public record FriendshipGetByCursorResponse(
	String nextCursorId,
	Integer totalCount,
	List<FriendshipSummary> followers
) {
	public static FriendshipGetByCursorResponse from(final CursorSummary<FriendshipSummary> cursorSummary) {
		return new FriendshipGetByCursorResponse(
			cursorSummary.nextCursorId(),
			cursorSummary.summaryCount(),
			cursorSummary.summaries()
		);
	}
}
