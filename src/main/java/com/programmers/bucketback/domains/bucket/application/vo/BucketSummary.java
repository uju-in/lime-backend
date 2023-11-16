package com.programmers.bucketback.domains.bucket.application.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.bucketback.domains.common.CursorIdParser;

public record BucketSummary(
	String cursorId,
	Long bucketId,
	String name,
	Integer budget,
	LocalDateTime createdAt,
	List<ItemImage> itemImages
) implements CursorIdParser {

	@Override
	public String cursorId() {
		return cursorId;
	}
}
