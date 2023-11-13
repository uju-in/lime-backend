package com.programmers.bucketback.domains.bucket.application.vo;

import java.util.List;

import com.programmers.bucketback.domains.bucket.api.dto.response.BucketGetResponse;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.item.application.vo.ItemInfo;

public record GetBucketServiceResponse(
	Bucket bucket,
	List<ItemInfo> itemInfos
) {
	public BucketGetResponse toBucketGetResponse() {
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
