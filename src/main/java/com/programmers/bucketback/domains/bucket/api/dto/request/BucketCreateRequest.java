package com.programmers.bucketback.domains.bucket.api.dto.request;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.BucketContent;
import com.programmers.bucketback.domains.common.Hobby;

import jakarta.validation.constraints.NotNull;

public record BucketCreateRequest(

	@NotNull // enumvalidater로 추가 예정
	String hobby,

	@NotNull
	String bucketName,

	Integer bucketBudget,

	@NotNull
	List<Long> itemIds
) {

	public BucketContent toContent() {
		return BucketContent.builder()
			.hobby(Hobby.valueOf(hobby))
			.name(bucketName)
			.budget(bucketBudget)
			.itemIds(itemIds)
			.build();
	}
}
