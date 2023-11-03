package com.programmers.bucketback.domains.item.application;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.item.application.dto.AddMemberItemServiceRequest;
import com.programmers.bucketback.domains.item.application.dto.GetItemNamesServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.GetItemServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.ItemNameGetResult;
import com.programmers.bucketback.domains.item.application.vo.ItemInfo;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.MemberItem;
import com.programmers.bucketback.domains.review.application.ReviewStatistics;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final AddMemberItemService addMemberItemService;
	private final MemberItemChecker memberItemChecker;
	private final ItemReader itemReader;
	private final ReviewStatistics reviewStatistics;
	private final MemberItemReader memberItemReader;
	private final MemberItemRemover memberItemRemover;
	private final ItemFinder itemFinder;
	private final ItemMapper itemMapper;

	public void addItem(final AddMemberItemServiceRequest request) {
		Long memberId = MemberUtils.getCurrentMemberId();
		addMemberItemService.addMemberItems(request.itemIds(), memberId);
	}

	public GetItemServiceResponse getItemDetails(final Long itemId) {
		boolean isMemberItem = false;

		Item item = itemReader.read(itemId);
		if (MemberUtils.isLoggedIn()) {
			Long memberId = MemberUtils.getCurrentMemberId();
			isMemberItem = memberItemChecker.existMemberItemByMemberId(memberId, item);
		}

		Double itemAvgRating = reviewStatistics.getReviewAvgByItemId(itemId);
		ItemInfo itemInfo = itemMapper.getItemInfo(item);

		return GetItemServiceResponse.builder()
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

	public GetItemNamesServiceResponse getItemNamesByKeyword(final String keyword) {
		final String trimedKeyword = keyword.trim();

		if (trimedKeyword.isEmpty()) {
			return new GetItemNamesServiceResponse(Collections.emptyList());
		}

		List<ItemNameGetResult> itemNameResults = itemFinder.getItemNamesByKeyword(trimedKeyword);
		return new GetItemNamesServiceResponse(itemNameResults);
	}
}
