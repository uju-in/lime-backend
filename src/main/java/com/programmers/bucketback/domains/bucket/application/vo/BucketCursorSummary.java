package com.programmers.bucketback.domains.bucket.application.vo;

import java.util.List;

import lombok.Builder;

@Builder
public record BucketCursorSummary(
	String cursorId,
	Long bucketId,
	String name,
	Integer budget,
	List<ItemImage> itemImages
) {
	public static BucketCursorSummary of(
		final String cursorId,
		final BucketSummary bucketSummary
	) {
		return BucketCursorSummary.builder()
			.cursorId(cursorId)
			.bucketId(bucketSummary.getBucketId())
			.name(bucketSummary.getBucketName())
			.budget(bucketSummary.getBucketBudget())
			.itemImages(bucketSummary.getItemImages())
			.build();
	}
}
