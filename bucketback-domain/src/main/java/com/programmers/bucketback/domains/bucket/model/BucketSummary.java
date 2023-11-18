package com.programmers.bucketback.domains.bucket.model;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.bucketback.common.cursor.CursorIdParser;

public record BucketSummary(
	String cursorId,
	Long bucketId,
	String name,
	Integer budget,
	LocalDateTime createdAt,
	List<ItemImage> itemImages
) implements CursorIdParser {
}
