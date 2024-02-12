package com.programmers.lime.domains.favorite.api.dto.request;

import java.util.List;

import com.programmers.lime.domains.item.model.MemberItemIdRegistry;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MemberItemCreateRequest(

	@Schema(description = "여러 아이템 id", example = "[1, 2, 3]")
	@NotNull(message = "아이템 목록은 필수 값 입니다.")
	List<Long> itemIds,

	@Schema(description = "아이템 폴더 id", example = "1")
	Long folderId
) {
	public MemberItemIdRegistry toMemberItemIdRegistry() {
		return new MemberItemIdRegistry(itemIds, folderId);
	}
}
