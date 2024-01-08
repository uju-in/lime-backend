package com.programmers.lime.domains.comment.repository;

import java.time.LocalDateTime;

import com.programmers.lime.common.cursor.CursorIdParser;
import com.programmers.lime.domains.member.model.MemberInfo;

public record CommentSummary(
	String cursorId,
	MemberInfo memberInfo,
	Long commentId,
	String content,
	boolean isAdopted,
	LocalDateTime createdAt
) implements CursorIdParser {
}
