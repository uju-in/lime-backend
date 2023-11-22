package com.programmers.bucketback.domains.review.application.dto;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.member.model.MemberInfo;

public record ReviewGetServiceResponse(
	Long reviewId,
	int rate,
	String content,
	LocalDateTime createdAt,
	LocalDateTime updatedAt,
	MemberInfo memberInfo
) {
}
