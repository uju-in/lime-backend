package com.programmers.bucketback.domains.bucket.application.vo;

import java.time.LocalDateTime;
import java.util.List;

public record BucketSummary(
	String cursorId,
	Long bucketId,
	String bucketName,
	Integer bucketBudget,
	LocalDateTime createdAt,
	List<ItemImage> itemImages
) {
}
