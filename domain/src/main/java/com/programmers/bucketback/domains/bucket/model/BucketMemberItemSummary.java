package com.programmers.bucketback.domains.bucket.model;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.item.model.ItemInfo;

public record BucketMemberItemSummary(
	String cursorId,
	boolean isSelected,
	LocalDateTime createdAt,
	ItemInfo itemInfo
) {
}