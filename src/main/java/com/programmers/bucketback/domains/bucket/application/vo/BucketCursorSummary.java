package com.programmers.bucketback.domains.bucket.application.vo;

import java.util.List;

import com.programmers.bucketback.domains.bucket.repository.BucketItemImage;

import lombok.Builder;

@Builder
public record BucketCursorSummary(
	String cursorId,
	Long bucketId,
	String bucketName,
	Integer bucketBudget,
	List<BucketItemImage> bucketItemImages
) {
	public static BucketCursorSummary of(
		String cursorId,
		BucketSummary bucketSummary
	) {
		return BucketCursorSummary.builder()
			.cursorId(cursorId)
			.bucketId(bucketSummary.bucketId())
			.bucketName(bucketSummary.bucketName())
			.bucketBudget(bucketSummary.bucketBudget())
			.bucketItemImages(bucketSummary.bucketImages())
			.build();
	}
}
