package com.programmers.lime.domains.review.model;

import com.programmers.lime.common.cursor.CursorIdParser;
import com.programmers.lime.domains.member.model.MemberInfo;

import lombok.Builder;

@Builder
public record ReviewCursorSummary(
	String cursorId,

	MemberInfo memberInfo,

	ReviewSummary reviewSummary,

	boolean isReviewed
	) implements CursorIdParser {
}
