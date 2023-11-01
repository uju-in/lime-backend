package com.programmers.bucketback.domains.bucket.api.dto.request;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketContent;
import com.programmers.bucketback.domains.common.Hobby;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record BucketUpdateRequest(

	@Schema(description = "취미", example = "농구")
	@NotNull(message = "취미를 입력하세요") // enumvalidater로 추가 예정
	String hobby,

	@Schema(description = "버킷 이름", example = "유러피안 농구")
	@NotNull(message = "버킷 이름을 입력하세요")
	String bucketName,

	@Schema(description = "예산", example = "100000")
	Integer bucketBudget,

	@Schema(description = "여러 아이템 id")
	@NotNull(message = "아이템 id를 입력하세요")
	List<Long> bucketItemIds
) {

	public BucketContent toContent() {
		return BucketContent.builder()
			.hobby(Hobby.valueOf(hobby))
			.bucketName(bucketName)
			.bucketBudget(bucketBudget)
			.bucketItemIds(bucketItemIds)
			.build();
	}
}
