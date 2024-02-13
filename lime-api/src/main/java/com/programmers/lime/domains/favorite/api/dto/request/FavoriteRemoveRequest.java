package com.programmers.lime.domains.favorite.api.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record FavoriteRemoveRequest(
	@Schema(description = "여러 찜한 아이템 id", example = "1, 2, 3")
	List<Long> favoriteItemIds,

	@Schema(description = "여러 폴더 id", example = "1, 2, 3")
	List<Long> folderIds
) {
}
