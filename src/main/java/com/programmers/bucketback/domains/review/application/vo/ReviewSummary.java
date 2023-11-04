package com.programmers.bucketback.domains.review.application.vo;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.member.application.vo.MemberInfo;

import lombok.Builder;

@Builder
public record ReviewSummary(

	MemberInfo memberInfo,

	Long reviewId,

	int rate,

	String content,

	LocalDateTime createdAt,

	LocalDateTime updatedAt
) {

}
