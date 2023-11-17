package com.programmers.bucketback.domains.item.api.dto.request;

import java.util.List;

import com.programmers.bucketback.domains.bucket.model.ItemIdRegistry;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MemberItemAddRequest(

	@Schema(description = "여러 아이템 id", example = "[1, 2, 3]")
	@NotNull(message = "아이템 목록은 필수 값 입니다.")
	List<Long> itemIds
) {
	public ItemIdRegistry toAddMemberItemServiceRequest() {
		return new ItemIdRegistry(itemIds);
	}
}
