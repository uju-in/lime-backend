package com.programmers.bucketback.domains.bucket.application.vo;

public record BucketSummary(
	Long bucketId,
	String bucketName,
	String bucketBudget,
	String bucketImage
) {
}
