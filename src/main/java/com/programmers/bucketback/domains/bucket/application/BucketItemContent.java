package com.programmers.bucketback.domains.bucket.application;

import java.util.List;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketItem;

public record BucketItemContent(
	/** refactor : itemContent와 같은 아이템 정보가 담긴 객체로 반환한다. */
	List<BucketItem> bucketItems,
	int bucketIemCounts
) {
	public static BucketItemContent from(final Bucket bucket){
		return new BucketItemContent(bucket.getBucketItems(), bucket.getBucketItems().size());
	}
}
