package com.programmers.bucketback.domains.comment.repository;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.member.application.vo.MemberInfo;

public record CommentSummary(
	String cursorId,
	MemberInfo memberInfo,
	Long commentId,
	String content,
	boolean isAdopted,
	LocalDateTime createdAt
) {
}
