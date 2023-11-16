package com.programmers.bucketback.domains.bucket.api.dto.request;

import java.util.List;

import com.programmers.bucketback.Hobby;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
import com.programmers.bucketback.domains.bucket.model.ItemIdRegistry;
import com.programmers.bucketback.global.annotation.Enum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BucketUpdateRequest(

	@Schema(description = "취미", example = "농구")
	@NotNull(message = "취미를 입력하세요")
	@Enum
	Hobby hobby,

	@Schema(description = "버킷 이름", example = "유러피안 농구")
	@NotNull(message = "버킷 이름을 입력하세요")
	@Size(min = 1, max = 25, message = "버킷 이릉은 1자 이상 25자 이하입니다.")
	String name,

	@Schema(description = "예산", example = "100000")
	@Min(value = 1, message = "최소 에산은 0원 초과입니다.")
	Integer budget,

	@Schema(description = "여러 아이템 id", example = "[1,2]")
	@NotEmpty(message = "아이템 id를 최소 1개 이상 입력하세요")
	List<Long> itemIds
) {

	public BucketInfo toInfo() {
		return new BucketInfo(hobby, name, budget);
	}

	public ItemIdRegistry toRegistry() {
		return new ItemIdRegistry(itemIds);
	}
}
