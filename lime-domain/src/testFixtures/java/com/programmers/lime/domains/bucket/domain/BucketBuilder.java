package com.programmers.lime.domains.bucket.domain;

import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.common.model.ItemIdRegistryBuilder;
import com.programmers.lime.domains.item.domain.ItemBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BucketBuilder {

	public static Bucket build(final BucketInfo bucketInfo) {
		Long memberId = 1L;

		Bucket bucket = new Bucket(bucketInfo, memberId);
		setBucketId(bucket);

		return bucket;
	}

	public static Bucket build() {
		Long memberId = 1L;

		BucketInfo bucketInfo = buildBucketInfo();
		Bucket bucket = new Bucket(bucketInfo, memberId);
		setBucketId(bucket);

		return bucket;
	}

	public static BucketInfo buildBucketInfo(
		final Hobby hobby,
		final String bucketName,
		final Integer budget
	) {
		return new BucketInfo(hobby, bucketName, budget);
	}

	public static BucketInfo buildBucketInfo() {
		return new BucketInfo(Hobby.BASKETBALL, "유러피안 농구", 100000);
	}

	public static List<BucketItem> buildBucketItems(Long bucketId, ItemIdRegistry itemIdRegistry) {
		return itemIdRegistry.itemIds().stream()
			.map(itemId -> new BucketItem(bucketId,itemId))
			.toList();
	}

	private static void setBucketId(final Bucket bucket) {
		ReflectionTestUtils.setField(
			bucket,
			"id",
			1L
		);
	}

}
