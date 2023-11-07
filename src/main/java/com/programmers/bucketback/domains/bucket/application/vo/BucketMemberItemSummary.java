package com.programmers.bucketback.domains.bucket.application.vo;

import java.time.LocalDateTime;

public record BucketMemberItemSummary(
	String cursorId,
	Long itemId,
	String name,
	Integer price,
	String image,
	boolean isSelected,
	LocalDateTime createdAt
) {
}