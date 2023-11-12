package com.programmers.bucketback.domains.bucket.api.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.item.application.vo.ItemInfo;

import lombok.Builder;

@Builder
public record BucketGetResponse(
	Hobby hobby,
	String name,

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer budget,

	Long memberId,
	Long bucketId,
	List<ItemInfo> itemInfos
) {
	public static BucketGetResponse of(
		final Bucket bucket,
		final List<ItemInfo> itemInfos
	) {
		return BucketGetResponse.builder()
			.hobby(bucket.getBucketInfo().getHobby())
			.name(bucket.getBucketInfo().getName())
			.budget(bucket.getBucketInfo().getBudget())
			.memberId(bucket.getMemberId())
			.bucketId(bucket.getMemberId())
			.itemInfos(itemInfos)
			.build();
	}
}
