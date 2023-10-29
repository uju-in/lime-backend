package com.programmers.bucketback.domains.bucket.application;

import java.util.List;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketItem;

public record BucketItemContent(
	List<BucketItem> bucketItems,
	int bucketIemCounts
) {
	public static BucketItemContent from(final Bucket bucket){
		return new BucketItemContent(bucket.getBucketItems(), bucket.getBucketItems().size());
	}
}
