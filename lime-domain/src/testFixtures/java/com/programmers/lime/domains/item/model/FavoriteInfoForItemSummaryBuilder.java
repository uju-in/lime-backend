package com.programmers.lime.domains.item.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteInfoForItemSummaryBuilder {

	public static FavoriteInfoForItemSummary build(Long itemId, Long favoriteCount) {
		return new FavoriteInfoForItemSummary(itemId, favoriteCount);
	}

	public static List<FavoriteInfoForItemSummary> buildMany(List<Long> ids) {
		return ids.stream().map(
			id -> build(id, id)
		).toList();
	}
}
