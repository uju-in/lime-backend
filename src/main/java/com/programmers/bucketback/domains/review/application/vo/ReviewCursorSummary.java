package com.programmers.bucketback.domains.review.application.vo;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.member.application.vo.MemberInfo;

import lombok.Builder;

@Builder
public record ReviewCursorSummary(
	String cursorId,

	MemberInfo memberInfo,

	Long reviewId,

	int rate,

	String content,

	LocalDateTime createdAt,

	LocalDateTime updatedAt
) {
	public static ReviewCursorSummary of(
		final String cursorId,
		final ReviewSummary reviewSummary
	) {
		return ReviewCursorSummary.builder()
			.cursorId(cursorId)
			.memberInfo(reviewSummary.memberInfo())
			.reviewId(reviewSummary.reviewId())
			.rate(reviewSummary.rate())
			.content(reviewSummary.content())
			.createdAt(reviewSummary.createdAt())
			.updatedAt(reviewSummary.updatedAt())
			.build();
	}
}
