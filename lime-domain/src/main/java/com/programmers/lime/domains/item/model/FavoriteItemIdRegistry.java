package com.programmers.lime.domains.item.model;

import java.util.List;

public record FavoriteItemIdRegistry(
	List<Long> itemIds,
	Long folderId
) {
}
