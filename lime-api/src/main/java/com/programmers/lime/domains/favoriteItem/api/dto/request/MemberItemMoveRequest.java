package com.programmers.lime.domains.favoriteItem.api.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MemberItemMoveRequest(

	@Schema(description = "이동할 폴더 id", example = "1")
	Long folderId,

	@Schema(description = "이동할 찜 아이템 목록", example = "[1, 2, 3]")
	@NotNull(message = "찜 아이템 목록은 필수 값 입니다.")
	List<Long> memberItemIds
) {
}
