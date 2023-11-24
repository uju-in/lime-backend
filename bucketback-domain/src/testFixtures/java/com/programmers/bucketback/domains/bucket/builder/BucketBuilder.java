package com.programmers.bucketback.domains.bucket.builder;

import java.util.Arrays;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.common.model.ItemIdRegistry;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BucketBuilder {

	public static Bucket build() {
		Long memberId = 1L;

		Bucket bucket = new Bucket(buildBucketInfo(), memberId);
		setBucketId(bucket);

		return bucket;
	}

	public static BucketInfo buildBucketInfo() {
		return new BucketInfo(
			Hobby.BASKETBALL,
			"nba 올스타 세트",
			100000
		);
	}

	public static ItemIdRegistry createItemIdRegistry() {
		return new ItemIdRegistry(Arrays.asList(1L, 2L, 3L));
	}

	private static void setBucketId(final Bucket bucket) {
		ReflectionTestUtils.setField(
			bucket,
			"id",
			1L
		);
	}
}
