package com.programmers.lime.domains.chat.model;

import java.time.LocalDateTime;

import com.programmers.lime.common.cursor.CursorIdParser;

public record ChatSummary(
	String cursorId,
	Long chatId,
	Long chatRoomId,
	Long memberId,
	String nickname,
	String profileImage,
	String message,
	LocalDateTime sendAt,
	ChatType chatType
) implements CursorIdParser {
}
