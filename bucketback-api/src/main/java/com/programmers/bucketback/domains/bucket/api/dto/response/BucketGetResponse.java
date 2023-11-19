package com.programmers.bucketback.domains.bucket.api.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.bucket.model.BucketGetServiceResponse;
import com.programmers.bucketback.domains.item.model.ItemInfo;

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
	public static BucketGetResponse from(final BucketGetServiceResponse response) {
		return BucketGetResponse.builder()
			.hobby(response.hobby())
			.name(response.name())
			.budget(response.budget())
			.memberId(response.memberId())
			.bucketId(response.bucketId())
			.itemInfos(response.itemInfos())
			.build();
	}
}
