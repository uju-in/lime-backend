package com.programmers.lime.domains.item.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.item.model.MemberItemFavoriteInfo;

public record MemberItemObjectGetResponse(
	int totalCount,
	List<MemberItemFavoriteInfo> favoriteInfos
){
}
