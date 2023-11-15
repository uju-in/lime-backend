package com.programmers.bucketback.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.item.application.dto.ItemGetByCursorServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.ItemGetNamesServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.ItemGetServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.ItemNameGetResult;
import com.programmers.bucketback.domains.item.application.dto.MemberItemAddServiceRequest;
import com.programmers.bucketback.domains.item.application.vo.ItemInfo;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.MemberItem;
import com.programmers.bucketback.domains.review.application.ReviewStatistics;

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

	public void addItem(final MemberItemAddServiceRequest request) {
		Long memberId = MemberUtils.getCurrentMemberId();
		memberItemAppender.addMemberItems(request.itemIds(), memberId);
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
		List<ItemNameGetResult> itemNameResults = itemFinder.getItemNamesByKeyword(keyword);

		return new ItemGetNamesServiceResponse(itemNameResults);
	}

	public ItemGetByCursorServiceResponse getItemsByCursor(
		final String keyword,
		final CursorPageParameters parameters
	) {
		return itemCursorReader.readByCursor(
			keyword,
			parameters
		);
	}
}
