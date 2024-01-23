package com.programmers.lime.domains.item.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.item.model.MemberItemFavoriteInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemFavoriteReader {

	private final List<IFavoriteReader> favoriteReaders;

	public List<MemberItemFavoriteInfo> readObjects(
		final Long folderId,
		final Long memberId
	) {

		List<MemberItemFavoriteInfo> memberItemFavoriteInfos = new ArrayList<>();

		for (IFavoriteReader favoriteReader : favoriteReaders) {
			List<MemberItemFavoriteInfo> memberItemFavoriteInfosPart = favoriteReader.readFavorites(folderId, memberId);
			memberItemFavoriteInfos.addAll(memberItemFavoriteInfosPart);
		}

		return memberItemFavoriteInfos;
	}
}
