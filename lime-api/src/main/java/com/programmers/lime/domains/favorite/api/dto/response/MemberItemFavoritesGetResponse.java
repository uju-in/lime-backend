package com.programmers.lime.domains.favorite.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.item.model.MemberItemFavoriteInfo;

public record MemberItemFavoritesGetResponse(
	int totalCount,
	List<MemberItemFavoriteInfo> favoriteInfos
){
}
