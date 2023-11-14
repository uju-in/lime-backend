package com.programmers.bucketback.domains.bucket.api.dto.request;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.ItemIdRegistry;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.global.annotation.Enum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record BucketCreateRequest(

	@Schema(description = "취미", example = "농구")
	@NotNull(message = "취미를 입력하세요")
	@Enum
	Hobby hobby,

	@Schema(description = "버킷 이름", example = "유러피안 농구")
	@NotNull(message = "버킷 이름을 입력하세요")
	String name,

	@Schema(description = "예산", example = "100000")
	Integer budget,

	@Schema(description = "여러 아이템 id", example = "[1,2]")
	@NotNull(message = "아이템 id를 입력하세요")
	List<Long> itemIds
) {

	public BucketInfo toInfo() {
		return new BucketInfo(hobby, name, budget);
	}

	public ItemIdRegistry toRegistry() {
		return new ItemIdRegistry(itemIds);
	}

}
