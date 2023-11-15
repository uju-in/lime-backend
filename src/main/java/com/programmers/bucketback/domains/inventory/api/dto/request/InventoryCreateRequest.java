package com.programmers.bucketback.domains.inventory.api.dto.request;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.ItemIdRegistry;
import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.global.annotation.Enum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record InventoryCreateRequest(

	@Schema(description = "취미", example = "농구")
	@Enum
	Hobby hobby,

	@Schema(description = "아이템 아이디 값", example = "[1,2,3]")
	@NotEmpty(message = "아이템 id를 최소 1개 이상 입력하세요")
	List<Long> itemIds

) {
	public ItemIdRegistry toRegistry() {
		return new ItemIdRegistry(itemIds);
	}
}
