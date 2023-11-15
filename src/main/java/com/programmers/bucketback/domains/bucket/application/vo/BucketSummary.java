package com.programmers.bucketback.domains.bucket.application.vo;

import java.time.LocalDateTime;
import java.util.List;

public record BucketSummary(
	String cursorId,
	Long bucketId,
	String name,
	Integer budget,
	LocalDateTime createdAt,
	List<ItemImage> itemImages
) {
}
