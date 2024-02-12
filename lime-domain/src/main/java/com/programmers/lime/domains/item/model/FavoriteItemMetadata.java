package com.programmers.lime.domains.item.model;

import com.programmers.lime.common.model.Hobby;

import lombok.Builder;

@Builder
public record FavoriteItemMetadata(
	Long itemId,
	Hobby hobby,
	String itemUrl,
	String imageUrl,
	int price,
	int favoriteCount,
	int reviewCount
){
}
