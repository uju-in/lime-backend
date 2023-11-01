package com.programmers.bucketback.domains.bucket.application.vo;

import java.util.List;

import lombok.Builder;

@Builder
public record BucketCursorSummary(
	String cursorId,
	Long bucketId,
	String bucketName,
	Integer bucketBudget,
	List<ItemImage> itemImages
) {
	public static BucketCursorSummary of(
		String cursorId,
		BucketSummary bucketSummary
	) {
		return BucketCursorSummary.builder()
			.cursorId(cursorId)
			.bucketId(bucketSummary.getBucketId())
			.bucketName(bucketSummary.getBucketName())
			.bucketBudget(bucketSummary.getBucketBudget())
			.itemImages(bucketSummary.getItemImages())
			.build();
	}
}
