package com.programmers.lime.domains.item.model;

import java.time.LocalDateTime;

import com.programmers.lime.common.model.FavoriteType;

import lombok.Builder;

@Builder
public record MemberItemFavoriteInfo(
	Long favoriteId,
	String originalName,
	FavoriteType type,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt,
	MemberItemFavoriteMetadata metadata
) {
}
