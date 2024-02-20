package com.programmers.lime.domains.favorite.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.lime.common.model.FavoriteType;
import com.programmers.lime.domains.favorite.api.dto.response.FavoritesGetResponse;
import com.programmers.lime.domains.favorite.application.dto.FavoriteItemCreateServiceResponse;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.domains.item.implementation.MemberItemAppender;
import com.programmers.lime.domains.item.implementation.MemberItemFavoriteReader;
import com.programmers.lime.domains.item.implementation.MemberItemFolderValidator;
import com.programmers.lime.domains.item.implementation.MemberItemRemover;
import com.programmers.lime.domains.item.implementation.MemberItemValidator;
import com.programmers.lime.domains.item.model.MemberItemFavoriteInfo;
import com.programmers.lime.domains.item.model.FavoriteItemIdRegistry;
import com.programmers.lime.global.util.MemberUtils;
import com.programmers.lime.redis.implement.ItemRanking;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteItemService {

	private final MemberItemAppender memberItemAppender;

	private final MemberItemFavoriteReader memberItemFavoriteReader;

	private final MemberItemRemover memberItemRemover;

	private final ItemReader itemReader;

	private final ItemRanking itemRanking;

	private final MemberUtils memberUtils;

	private final MemberItemValidator memberItemValidator;

	private final MemberItemFolderValidator memberItemFolderValidator;

	public FavoriteItemCreateServiceResponse createFavoriteItems(
		final FavoriteItemIdRegistry favoriteItemIdRegistry
	) {
		updateItemRanking(favoriteItemIdRegistry);

		Long memberId = memberUtils.getCurrentMemberId();
		List<Long> memberItemIds = memberItemAppender.appendMemberItems(
			favoriteItemIdRegistry.itemIds(),
			favoriteItemIdRegistry.folderId(),
			memberId
		);

		return new FavoriteItemCreateServiceResponse(memberItemIds);
	}

	private void updateItemRanking(final FavoriteItemIdRegistry favoriteItemIdRegistry) {
		List<String> items = favoriteItemIdRegistry.itemIds().stream()
			.map(itemReader::read)
			.map(Item::getName)
			.toList();

		for (String itemName : items) {
			itemRanking.increasePoint(itemName, 1);
		}
	}

	public FavoritesGetResponse getFavorites(
		final Long folderId,
		final FavoriteType favoriteTypeCondition
	) {
		Long memberId = memberUtils.getCurrentMemberId();

		memberItemFolderValidator.validateExsitMemberItemFolder(folderId, memberId);
		List<MemberItemFavoriteInfo> memberItemFavoriteInfos =
			memberItemFavoriteReader.readObjects(folderId, memberId, favoriteTypeCondition);

		return new FavoritesGetResponse(memberItemFavoriteInfos.size(), memberItemFavoriteInfos);
	}

	public void moveFavoriteItems(
		final Long folderId,
		final List<Long> memberItemIds
	) {
		Long memberId = memberUtils.getCurrentMemberId();

		memberItemValidator.validateExistMemberIdAndMemberItemId(memberId, memberItemIds);
		memberItemFolderValidator.validateExsitMemberItemFolder(folderId, memberId);

		memberItemAppender.moveMemberItems(folderId, memberItemIds);
	}

	public void removeFavoriteItems(
		final List<Long> memberItemIds
	) {

		if(memberItemIds == null || memberItemIds.isEmpty()) {
			return;
		}

		Long memberId = memberUtils.getCurrentMemberId();

		memberItemValidator.validateExistMemberIdAndMemberItemId(memberId, memberItemIds);

		for (Long memberItemId : memberItemIds) {
			memberItemRemover.remove(memberItemId);
		}
	}
}
