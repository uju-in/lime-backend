package com.programmers.lime.domains.bucket.model;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.lime.common.cursor.CursorIdParser;

public record BucketSummary(
	String cursorId,
	Long bucketId,
	String name,
	Integer budget,
	Integer totalPrice,
	LocalDateTime createdAt,
	List<ItemImage> itemImages
) implements CursorIdParser {
}
