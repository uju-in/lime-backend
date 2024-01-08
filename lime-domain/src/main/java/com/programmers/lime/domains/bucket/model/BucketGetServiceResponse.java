package com.programmers.lime.domains.bucket.model;

import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.bucket.domain.Bucket;
import com.programmers.lime.domains.item.model.ItemInfo;

import lombok.Builder;

@Builder
public record BucketGetServiceResponse(
	Hobby hobby,
	String name,
	Integer budget,
	Integer totalPrice,
	Long memberId,
	Long bucketId,
	List<ItemInfo> itemInfos
) {
	public static BucketGetServiceResponse of(
		final Bucket bucket,
		final Integer totalPrice,
		final List<ItemInfo> itemInfos
	) {
		return BucketGetServiceResponse.builder()
			.hobby(bucket.getHobby())
			.name(bucket.getName())
			.budget(bucket.getBudget())
			.totalPrice(totalPrice)
			.memberId(bucket.getMemberId())
			.bucketId(bucket.getId())
			.itemInfos(itemInfos)
			.build();
	}
}
