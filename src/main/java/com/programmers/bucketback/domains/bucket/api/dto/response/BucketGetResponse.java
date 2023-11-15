package com.programmers.bucketback.domains.bucket.api.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programmers.bucketback.domains.bucket.application.vo.BucketGetServiceResponse;
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
	public static BucketGetResponse from(final BucketGetServiceResponse response) {
		return BucketGetResponse.builder()
			.hobby(response.bucket().getHobby())
			.name(response.bucket().getName())
			.budget(response.bucket().getBudget())
			.memberId(response.bucket().getMemberId())
			.bucketId(response.bucket().getId())
			.itemInfos(response.itemInfos())
			.build();
	}
}
