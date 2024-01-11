package com.programmers.lime.domains.bucket.model;

import java.util.List;

import com.programmers.lime.domains.bucket.domain.Bucket;

public record BucketProfile(
	Long id,
	String hobby,
	List<String> images
) {
	public static BucketProfile of(
		final Bucket bucket,
		final List<String> itemImages
	) {
		return new BucketProfile(
			bucket.getId(),
			bucket.getHobby().getName(),
			itemImages);
	}
}
