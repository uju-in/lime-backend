package com.programmers.lime.domains.item.model;

import java.util.Arrays;
import java.util.List;

public class MemberItemIdRegistryBuilder {

	public static FavoriteItemIdRegistry build() {
		return new FavoriteItemIdRegistry(Arrays.asList(1L, 2L, 3L), 1L);
	}

	public static FavoriteItemIdRegistry build(List<Long> ids, Long folderId) {
		return new FavoriteItemIdRegistry(ids, folderId);
	}
}
