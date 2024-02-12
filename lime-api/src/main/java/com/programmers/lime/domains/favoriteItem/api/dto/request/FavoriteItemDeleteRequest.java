package com.programmers.lime.domains.favoriteItem.api.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record FavoriteItemDeleteRequest (
	@Schema(description = "여러 아이템 id", example = "1, 2, 3")
	List<Long> itemIds,

	@Schema(description = "여러 폴더 id", example = "1, 2, 3")
	List<Long> folderIds
) {
}
