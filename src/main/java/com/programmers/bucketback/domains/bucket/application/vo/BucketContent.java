package com.programmers.bucketback.domains.bucket.application.vo;

import java.util.List;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.common.Hobby;

import lombok.Builder;

@Builder
public record BucketContent(

	Hobby hobby,
	String bucketName,
	Integer bucketBudget,
	List<Long> bucketItemIds,
	Long memberId,
	Long bucketId
) {
	public static BucketContent from(final Bucket bucket){
		return BucketContent.builder()
			.hobby(bucket.getHobby())
			.bucketName(bucket.getBucketInfo().getName())
			.bucketBudget(bucket.getBucketInfo().getBudget())
			.bucketId(bucket.getId())
			.memberId(bucket.getMemberId())
			.build();
	}
}
