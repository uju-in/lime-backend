package com.programmers.lime.domains.item.implementation;

import java.util.List;

import com.programmers.lime.common.model.FavoriteType;
import com.programmers.lime.domains.item.model.MemberItemFavoriteInfo;

public interface IFavoriteReader {

	List<MemberItemFavoriteInfo> readFavorites(final Long folderId, final Long memberId);
	FavoriteType getFavoriteType();
}
