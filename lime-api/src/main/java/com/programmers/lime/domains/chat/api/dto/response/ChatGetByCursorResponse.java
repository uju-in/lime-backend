package com.programmers.lime.domains.chat.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.chat.application.dto.response.ChatGetCursorServiceResponse;
import com.programmers.lime.domains.chat.model.ChatSummary;

public record ChatGetByCursorResponse(
	String nextCursorId,
	List<ChatSummary> chatSummaries
) {
	public static ChatGetByCursorResponse from(final ChatGetCursorServiceResponse response) {
		return new ChatGetByCursorResponse(
			response.cursorSummary().nextCursorId(),
			response.cursorSummary().summaries()
		);
	}

}
