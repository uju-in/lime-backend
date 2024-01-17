package com.programmers.lime.domains.item.api.dto.request;

import java.util.List;

import com.programmers.lime.common.model.ItemRemovalList;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MemberItemDeleteRequest(

	@Schema(description = "여러 아이템 id", example = "1, 2, 3")
	@NotNull(message = "아이템 목록은 필수 값 입니다.")
	List<Long> itemIds,

	@Schema(description = "폴더 id", example = "1")
	@NotNull(message = "폴더 id는 필수 값 입니다.")
	Long folderId
) {
	public ItemRemovalList toItemRemovalList() {
		return new ItemRemovalList(itemIds);
	}
}
