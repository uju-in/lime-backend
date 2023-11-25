package com.programmers.bucketback.domains.bucket.api.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programmers.bucketback.domains.bucket.model.BucketGetServiceResponse;
import com.programmers.bucketback.domains.item.model.ItemInfo;

import lombok.Builder;

@Builder
public record BucketGetResponse(
	String hobby,
	String name,

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer budget,

	Integer totalPrice,
	Long memberId,
	Long bucketId,
	List<ItemInfo> itemInfos
) {
	public static BucketGetResponse from(final BucketGetServiceResponse response) {
		return BucketGetResponse.builder()
			.hobby(response.hobby().getHobbyValue())
			.name(response.name())
			.budget(response.budget())
			.totalPrice(response.totalPrice())
			.memberId(response.memberId())
			.bucketId(response.bucketId())
			.itemInfos(response.itemInfos())
			.build();
	}
}
