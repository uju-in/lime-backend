package com.programmers.lime.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.common.model.ItemRemovalList;
import com.programmers.lime.domains.item.api.dto.response.MemberItemFavoritesGetResponse;
import com.programmers.lime.domains.item.application.dto.MemberItemCreateServiceResponse;
import com.programmers.lime.domains.item.application.dto.ItemGetByCursorServiceResponse;
import com.programmers.lime.domains.item.application.dto.ItemGetNamesServiceResponse;
import com.programmers.lime.domains.item.application.dto.ItemGetServiceResponse;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.MemberItem;
import com.programmers.lime.domains.item.implementation.ItemCursorReader;
import com.programmers.lime.domains.item.implementation.ItemFinder;
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.domains.item.implementation.MemberItemAppender;
import com.programmers.lime.domains.item.implementation.MemberItemChecker;
import com.programmers.lime.domains.item.implementation.MemberItemFolderValidator;
import com.programmers.lime.domains.item.implementation.MemberItemFavoriteReader;
import com.programmers.lime.domains.item.implementation.MemberItemReader;
import com.programmers.lime.domains.item.implementation.MemberItemRemover;
import com.programmers.lime.domains.item.model.ItemCursorSummary;
import com.programmers.lime.domains.item.model.ItemInfo;
import com.programmers.lime.domains.item.model.ItemSortCondition;
import com.programmers.lime.domains.item.model.MemberItemIdRegistry;
import com.programmers.lime.domains.item.model.MemberItemFavoriteInfo;
import com.programmers.lime.domains.review.implementation.ReviewReader;
import com.programmers.lime.domains.review.implementation.ReviewStatistics;
import com.programmers.lime.global.util.MemberUtils;
import com.programmers.lime.redis.dto.ItemRankingServiceResponse;
import com.programmers.lime.redis.implement.ItemRanking;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final MemberItemAppender memberItemAppender;

	private final MemberItemChecker memberItemChecker;

	private final ReviewStatistics reviewStatistics;

	private final MemberItemReader memberItemReader;

	private final MemberItemRemover memberItemRemover;

	private final ItemFinder itemFinder;

	private final ItemCursorReader itemCursorReader;

	private final ItemRanking itemRanking;

	private final ReviewReader reviewReader;

	private final MemberUtils memberUtils;

	private final MemberItemFolderValidator memberItemFolderValidator;

	private final ItemReader itemReader;

	private final MemberItemFavoriteReader memberItemFavoriteReader;

	public MemberItemCreateServiceResponse createMemberItems(
		final MemberItemIdRegistry memberItemIdRegistry
	) {
		updateItemRanking(memberItemIdRegistry);

		Long memberId = memberUtils.getCurrentMemberId();
		List<Long> memberItemIds = memberItemAppender.appendMemberItems(
			memberItemIdRegistry.itemIds(),
			memberItemIdRegistry.folderId(),
			memberId
		);

		return new MemberItemCreateServiceResponse(memberItemIds);
	}

	private void updateItemRanking(final MemberItemIdRegistry memberItemIdRegistry) {
		List<String> items = memberItemIdRegistry.itemIds().stream()
			.map(itemReader::read)
			.map(Item::getName)
			.toList();

		for (String itemName : items) {
			itemRanking.increasePoint(itemName, 1);
		}
	}

	public ItemGetServiceResponse getItem(final Long itemId) {
		boolean isMemberItem;

		Item item = itemReader.read(itemId);
		ItemInfo itemInfo = ItemInfo.from(item);

		Long memberId = memberUtils.getCurrentMemberId();

		isMemberItem = memberItemChecker.existMemberItemByMemberId(memberId, item);
		double itemAvgRating = reviewStatistics.getReviewAvgByItemId(itemId);
		boolean isReviewed = reviewReader.existsReviewByMemberIdAndItemId(memberId, itemId);
		int favoriteCount = memberItemReader.countByItemId(itemId);

		return ItemGetServiceResponse.builder()
			.itemInfo(itemInfo)
			.isMemberItem(isMemberItem)
			.itemUrl(item.getUrl())
			.itemAvgRate(itemAvgRating)
			.favoriteCount(favoriteCount)
			.isReviewed(isReviewed)
			.build();
	}

	public void removeMemberItems(
		final Long folderId,
		final ItemRemovalList itemRemovalList
	) {
		Long memberId = memberUtils.getCurrentMemberId();

		memberItemFolderValidator.validateExsitMemberItemFolder(folderId, memberId);

		for (Long itemId : itemRemovalList.itemIds()) {
			MemberItem memberItem = memberItemReader.readByItemIdAndFolderId(itemId, folderId);
			memberItemRemover.remove(memberItem.getId());
		}
	}

	public ItemGetNamesServiceResponse getItemNamesByKeyword(final String keyword) {
		List<ItemInfo> itemNameGetResults = itemFinder.getItemNamesByKeyword(keyword);

		return new ItemGetNamesServiceResponse(itemNameGetResults);
	}

	public ItemGetByCursorServiceResponse getItemsByCursor(
		final String keyword,
		final CursorPageParameters parameters,
		final String itemSortCondition,
		final String hobbyName
	) {
		Hobby hobby = Hobby.from(hobbyName);

		ItemSortCondition sortCondition = ItemSortCondition.from(itemSortCondition);

		CursorSummary<ItemCursorSummary> itemCursorSummaryCursorSummary = itemCursorReader.readByCursor(
			keyword,
			parameters,
			sortCondition,
			hobby
		);

		int itemTotalCount = itemReader.getItemTotalCountByKeyword(keyword, hobby);

		return new ItemGetByCursorServiceResponse(
			itemTotalCount,
			itemCursorSummaryCursorSummary
		);
	}

	public MemberItemFavoritesGetResponse getMemberItemFavorites(
		final Long folderId
	) {
		Long memberId = memberUtils.getCurrentMemberId();

		memberItemFolderValidator.validateExsitMemberItemFolder(folderId, memberId);
		List<MemberItemFavoriteInfo> memberItemFavoriteInfos = memberItemFavoriteReader.readObjects(folderId, memberId);

		return new MemberItemFavoritesGetResponse(memberItemFavoriteInfos.size(), memberItemFavoriteInfos);
	}


	public List<ItemRankingServiceResponse> getRanking() {
		return itemRanking.viewRanking();
	}
}
