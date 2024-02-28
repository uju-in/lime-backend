package com.programmers.lime.domains.favorite.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.favorite.application.dto.FavoriteItemCreateServiceResponse;

public record FavoriteItemCreateResponse(List<Long> itemIds) {
	public static FavoriteItemCreateResponse from(final FavoriteItemCreateServiceResponse response) {
		return new FavoriteItemCreateResponse(
			response.itemIds()
		);
	}
}
