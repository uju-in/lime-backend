package com.programmers.lime.domains.favorite.application.dto;

import java.util.List;

public record FavoriteItemCreateServiceResponse(
	List<Long> itemIds
) {
}
