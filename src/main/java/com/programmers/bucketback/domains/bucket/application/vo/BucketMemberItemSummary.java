package com.programmers.bucketback.domains.bucket.application.vo;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.item.application.vo.ItemInfo;

public record BucketMemberItemSummary(
	String cursorId,
	boolean isSelected,
	LocalDateTime createdAt,
	ItemInfo itemInfo
) {
}