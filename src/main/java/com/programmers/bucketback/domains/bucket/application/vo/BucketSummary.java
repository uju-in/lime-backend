package com.programmers.bucketback.domains.bucket.application.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.bucketback.domains.bucket.repository.BucketItemImage;

public record BucketSummary(
	Long bucketId,
	String bucketName,
	Integer bucketBudget,
	LocalDateTime createdAt,
	List<BucketItemImage> bucketImages
){

}
