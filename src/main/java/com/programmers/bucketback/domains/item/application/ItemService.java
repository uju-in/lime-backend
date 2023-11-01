package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.item.application.dto.AddMemberItemServiceRequest;
import com.programmers.bucketback.domains.item.application.dto.GetItemServiceResponse;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.review.application.ReviewStatistics;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final AddMemberItemService addMemberItemService;
	private final MemberItemChecker memberItemChecker;
	private final ItemReader itemReader;
	private final ReviewStatistics reviewStatistics;

	public void addItem(final AddMemberItemServiceRequest request) {
		Long memberId = MemberUtils.getCurrentMemberId();
		addMemberItemService.addMemberItems(request.itemIds(), memberId);
	}

	public GetItemServiceResponse getItemDetails(Long itemId) {
		boolean itemIsMine = false;

		Item item = itemReader.read(itemId);
		if (MemberUtils.isLoggedIn()) {
			Long memberId = MemberUtils.getCurrentMemberId();
			itemIsMine = memberItemChecker.existMemberItemByMemberId(memberId, item);
		}

		Double itemAvgRating = reviewStatistics.getReviewAvgByItemId(itemId);
		return GetItemServiceResponse.builder()
			.itemId(item.getId())
			.itemIsMine(itemIsMine)
			.itemUrl(item.getUrl())
			.itemAvgRate(itemAvgRating)
			.itemPrice(item.getPrice())
			.itemName(item.getName())
			.itemImage(item.getImage())
			.build();
	}
}
