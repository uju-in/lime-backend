package com.programmers.bucketback.domains.bucket.model;

import java.util.List;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.item.model.ItemInfo;

import lombok.Builder;

@Builder
public record BucketGetServiceResponse(
	Hobby hobby,
	String name,
	Integer budget,
	Long memberId,
	Long bucketId,
	List<ItemInfo> itemInfos
) {
	public static BucketGetServiceResponse of(
		final Bucket bucket,
		final List<ItemInfo> itemInfos
	) {
		return BucketGetServiceResponse.builder()
			.hobby(bucket.getHobby())
			.name(bucket.getName())
			.budget(bucket.getBudget())
			.memberId(bucket.getMemberId())
			.bucketId(bucket.getId())
			.itemInfos(itemInfos)
			.build();
	}
}
