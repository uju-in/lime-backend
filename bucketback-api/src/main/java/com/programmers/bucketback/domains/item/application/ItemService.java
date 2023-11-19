package com.programmers.bucketback.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.model.ItemIdRegistry;
import com.programmers.bucketback.domains.item.application.dto.ItemAddServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.ItemGetNamesServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.ItemGetServiceResponse;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.MemberItem;
import com.programmers.bucketback.domains.item.implementation.ItemCursorReader;
import com.programmers.bucketback.domains.item.implementation.ItemFinder;
import com.programmers.bucketback.domains.item.implementation.ItemReader;
import com.programmers.bucketback.domains.item.implementation.MemberItemAppender;
import com.programmers.bucketback.domains.item.implementation.MemberItemChecker;
import com.programmers.bucketback.domains.item.implementation.MemberItemReader;
import com.programmers.bucketback.domains.item.implementation.MemberItemRemover;
import com.programmers.bucketback.domains.item.model.ItemCursorSummary;
import com.programmers.bucketback.domains.item.model.ItemInfo;
import com.programmers.bucketback.domains.item.model.MemberItemSummary;
import com.programmers.bucketback.domains.review.implementation.ReviewStatistics;
import com.programmers.bucketback.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final MemberItemAppender memberItemAppender;
	private final MemberItemChecker memberItemChecker;
	private final ItemReader itemReader;
	private final ReviewStatistics reviewStatistics;
	private final MemberItemReader memberItemReader;
	private final MemberItemRemover memberItemRemover;
	private final ItemFinder itemFinder;
	private final ItemCursorReader itemCursorReader;

	public ItemAddServiceResponse addItem(final ItemIdRegistry itemIdRegistry) {
		Long memberId = MemberUtils.getCurrentMemberId();
		List<Long> memberItemIds = memberItemAppender.addMemberItems(itemIdRegistry.itemIds(), memberId);

		return new ItemAddServiceResponse(memberItemIds);
	}

	public ItemGetServiceResponse getItem(final Long itemId) {
		boolean isMemberItem = false;

		Item item = itemReader.read(itemId);
		Long memberId = MemberUtils.getCurrentMemberId();
		isMemberItem = memberItemChecker.existMemberItemByMemberId(memberId, item);

		Double itemAvgRating = reviewStatistics.getReviewAvgByItemId(itemId);
		ItemInfo itemInfo = ItemInfo.from(item);

		return ItemGetServiceResponse.builder()
			.itemInfo(itemInfo)
			.isMemberItem(isMemberItem)
			.itemUrl(item.getUrl())
			.itemAvgRate(itemAvgRating)
			.build();
	}

	public void removeMemberItem(final Long itemId) {
		Long memberId = MemberUtils.getCurrentMemberId();
		MemberItem memberItem = memberItemReader.read(itemId, memberId);
		memberItemRemover.remove(memberItem.getId());
	}

	public ItemGetNamesServiceResponse getItemNamesByKeyword(final String keyword) {
		List<ItemInfo> itemNameGetResults = itemFinder.getItemNamesByKeyword(keyword);

		return new ItemGetNamesServiceResponse(itemNameGetResults);
	}

	public CursorSummary<ItemCursorSummary> getItemsByCursor(
		final String keyword,
		final CursorPageParameters parameters
	) {
		return itemCursorReader.readByCursor(
			keyword,
			parameters
		);
	}

	public CursorSummary<MemberItemSummary> getMemberItemsByCursor(final CursorPageParameters parameters) {
		Long memberId = MemberUtils.getCurrentMemberId();

		return memberItemReader.readMemberItem(
			memberId,
			parameters
		);
	}
}
