package com.programmers.bucketback.domains.review.model;

import java.time.LocalDateTime;

import com.programmers.bucketback.common.cursor.CursorIdParser;
import com.programmers.bucketback.domains.member.model.MemberInfo;

import lombok.Builder;

@Builder
public record ReviewCursorSummary(
	String cursorId,

	MemberInfo memberInfo,

	Long reviewId,

	int rate,

	String content,

	boolean isReviewed,

	LocalDateTime createdAt,

	LocalDateTime updatedAt
) implements CursorIdParser {
}
