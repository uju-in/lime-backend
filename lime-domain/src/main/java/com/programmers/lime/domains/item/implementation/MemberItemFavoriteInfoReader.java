package com.programmers.lime.domains.item.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.common.model.FavoriteType;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.MemberItem;
import com.programmers.lime.domains.item.model.FavoriteItemMetadata;
import com.programmers.lime.domains.item.model.MemberItemFavoriteInfo;
import com.programmers.lime.domains.item.model.MemberItemFavoriteMetadata;
import com.programmers.lime.domains.review.implementation.ReviewReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemFavoriteInfoReader implements IFavoriteReader {

	private final MemberItemReader memberItemReader;

	private final ReviewReader reviewReader;

	@Override
	public List<MemberItemFavoriteInfo> readFavorites(final Long folderId, final Long memberId) {
		List<MemberItem> memberItems = memberItemReader.memberItemsByFolderIdAndMemberId(folderId, memberId);
		return memberItems.stream()
			.map(this::mapToMemberItemObjectInfo)
			.toList();
	}

	private MemberItemFavoriteInfo mapToMemberItemObjectInfo(final MemberItem memberItem) {
		return MemberItemFavoriteInfo.builder()
			.favoriteId(memberItem.getId())
			.originalName(memberItem.getItem().getName())
			.type(FavoriteType.ITEM)
			.createdAt(memberItem.getCreatedAt())
			.modifiedAt(memberItem.getModifiedAt())
			.metadata(toMetadata(memberItem))
			.build();
	}

	private MemberItemFavoriteMetadata toMetadata(final MemberItem memberItem) {
		Item item = memberItem.getItem();
		int favoriteCount = memberItemReader.countByItemId(item.getId());
		int reviewCount = reviewReader.countReviewByItemId(item.getId());
		int price = item.getPrice();

		return MemberItemFavoriteMetadata.builder().favoriteItemMetadata(
			FavoriteItemMetadata.builder()
				.itemId(item.getId())
				.hobby(item.getHobby())
				.itemUrl(item.getUrl())
				.imageUrl(item.getImage())
				.favoriteCount(favoriteCount)
				.reviewCount(reviewCount)
				.price(price)
				.build()
		).build();
	}
}
