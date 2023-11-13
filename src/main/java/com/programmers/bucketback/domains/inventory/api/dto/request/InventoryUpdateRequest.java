package com.programmers.bucketback.domains.inventory.api.dto.request;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.ItemIdRegistry;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record InventoryUpdateRequest(

	@Schema(description = "아이템 아이디 값", example = "[1,2,3]")
	@NotNull
	List<Long> itemIds
) {
	public ItemIdRegistry toRegistry() {
		return new ItemIdRegistry(itemIds);
	}
}
