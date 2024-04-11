package com.programmers.lime.domains.chat.application.dto.response;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.chat.model.ChatSummary;

public record ChatGetCursorServiceResponse(
	CursorSummary<ChatSummary> cursorSummary
) {
}
