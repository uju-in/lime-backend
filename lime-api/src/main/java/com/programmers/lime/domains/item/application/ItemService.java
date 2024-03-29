package com.programmers.lime.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.application.dto.ItemGetByCursorServiceResponse;
import com.programmers.lime.domains.item.application.dto.ItemGetNamesServiceResponse;
import com.programmers.lime.domains.item.application.dto.ItemGetServiceResponse;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.implementation.ItemCursorReader;
import com.programmers.lime.domains.item.implementation.ItemFinder;
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.domains.item.implementation.MemberItemChecker;
import com.programmers.lime.domains.item.implementation.MemberItemReader;
import com.programmers.lime.domains.item.model.ItemCursorSummary;
import com.programmers.lime.domains.item.model.ItemInfo;
import com.programmers.lime.domains.item.model.ItemSortCondition;
import com.programmers.lime.domains.review.implementation.ReviewReader;
import com.programmers.lime.domains.review.implementation.ReviewStatistics;
import com.programmers.lime.global.util.MemberUtils;
import com.programmers.lime.redis.dto.ItemRankingServiceResponse;
import com.programmers.lime.redis.implement.ItemRanking;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final MemberItemChecker memberItemChecker;

	private final ReviewStatistics reviewStatistics;

	private final MemberItemReader memberItemReader;

	private final ItemFinder itemFinder;

	private final ItemCursorReader itemCursorReader;

	private final ItemRanking itemRanking;

	private final ReviewReader reviewReader;

	private final MemberUtils memberUtils;

	private final ItemReader itemReader;


	public ItemGetServiceResponse getItem(final Long itemId) {
		boolean isMemberItem;

		Item item = itemReader.read(itemId);
		ItemInfo itemInfo = ItemInfo.from(item);

		Long memberId = memberUtils.getCurrentMemberId();

		isMemberItem = memberItemChecker.existMemberItemByMemberId(memberId, item);
		double itemAvgRating = reviewStatistics.getReviewAvgByItemId(itemId);
		boolean isReviewed = reviewReader.existsReviewByMemberIdAndItemId(memberId, itemId);
		int favoriteCount = memberItemReader.countByItemId(itemId);
		int reviewCount = reviewReader.countReviewByItemId(itemId);

		return ItemGetServiceResponse.builder()
			.itemInfo(itemInfo)
			.isMemberItem(isMemberItem)
			.itemUrl(item.getUrl())
			.itemAvgRate(itemAvgRating)
			.favoriteCount(favoriteCount)
			.isReviewed(isReviewed)
			.reviewCount(reviewCount)
			.hobbyName(item.getHobby().getName())
			.build();
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
		// 입력 받은 string을 enum으로 변환 (hobbyName, itemSortCondition)
		Hobby hobby = Hobby.from(hobbyName);
		ItemSortCondition sortCondition = ItemSortCondition.from(itemSortCondition);

		// 요청한 조건에 해당하는 아이템 중 size 만큼 커서 아이디와 아이템 아이디를 가져옴
		CursorSummary<ItemCursorSummary> itemCursorSummaryCursorSummary = itemCursorReader.readByCursor(
			keyword,
			parameters,
			sortCondition,
			hobby
		);

		// 요청한 조건에 해당하는 모든 아이템의 수를 itemTotalCount에 저장
		Long itemTotalCount = itemReader.getItemTotalCountByKeyword(keyword, hobby);

		return new ItemGetByCursorServiceResponse(
			itemTotalCount,
			itemCursorSummaryCursorSummary
		);
	}

	public List<ItemRankingServiceResponse> getRanking() {
		return itemRanking.viewRanking();
	}
}
