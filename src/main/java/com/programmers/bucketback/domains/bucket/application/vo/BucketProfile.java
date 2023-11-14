package com.programmers.bucketback.domains.bucket.application.vo;

import java.util.List;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.common.Hobby;

public record BucketProfile(
	Long id,
	Hobby hobby,
	List<String> images
) {
	public static BucketProfile of(
		final Bucket bucket,
		final List<String> itemImages
	) {
		return new BucketProfile(bucket.getId(), bucket.getHobby(), itemImages);
	}
}
