package com.programmers.bucketback.domains.inventory.api.dto.request;

import java.util.List;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.inventory.application.vo.InventoryCreateContent;
import com.programmers.bucketback.global.annotation.Enum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record InventoryCreateRequest(

	@Schema(description = "취미", example = "농구")
	@Enum
	Hobby hobby,

	@Schema(description = "아이템 아이디 값", example = "[1,2,3]")
	@NotNull
	List<Long> itemIds
) {
	public InventoryCreateContent toContent() {
		return new InventoryCreateContent(hobby, itemIds);
	}
}
