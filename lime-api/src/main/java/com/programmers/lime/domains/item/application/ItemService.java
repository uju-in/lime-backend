package com.programmers.lime.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.common.model.ItemRemovalList;
import com.programmers.lime.domains.item.application.dto.ItemAddServiceResponse;
import com.programmers.lime.domains.item.application.dto.ItemGetByCursorServiceResponse;
import com.programmers.lime.domains.item.application.dto.ItemGetNamesServiceResponse;
import com.programmers.lime.domains.item.application.dto.ItemGetServiceResponse;
import com.programmers.lime.domains.item.application.dto.MemberItemFolderGetServiceResponse;
import com.programmers.lime.domains.item.application.dto.MemberItemGetServiceResponse;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.MemberItem;
import com.programmers.lime.domains.item.implementation.ItemCursorReader;
import com.programmers.lime.domains.item.implementation.ItemFinder;
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.domains.item.implementation.MemberItemAppender;
import com.programmers.lime.domains.item.implementation.MemberItemChecker;
import com.programmers.lime.domains.item.implementation.MemberItemFolderValidator;
import com.programmers.lime.domains.item.implementation.MemberItemFolderModifier;
import com.programmers.lime.domains.item.implementation.MemberItemFolderReader;
import com.programmers.lime.domains.item.implementation.MemberItemReader;
import com.programmers.lime.domains.item.implementation.MemberItemRemover;
import com.programmers.lime.domains.item.model.ItemCursorSummary;
import com.programmers.lime.domains.item.model.ItemInfo;
import com.programmers.lime.domains.item.model.MemberItemFolderCursorSummary;
import com.programmers.lime.domains.item.model.MemberItemSummary;
import com.programmers.lime.domains.review.implementation.ReviewReader;
import com.programmers.lime.domains.review.implementation.ReviewStatistics;
import com.programmers.lime.global.util.MemberUtils;
import com.programmers.lime.redis.dto.ItemRankingServiceResponse;
import com.programmers.lime.redis.implement.ItemRanking;
import com.programmers.lime.domains.item.implementation.MemberItemFolderAppender;

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

	private final MemberUtils memberUtils;

	private final ItemRanking itemRanking;

	private final ReviewReader reviewReader;

	private final MemberItemFolderAppender memberItemFolderAppender;

	private final MemberItemFolderReader memberItemFolderReader;

	private final MemberItemFolderModifier memberItemFolderModifier;

	private final MemberItemFolderValidator memberItemFolderValidator;

	public ItemAddServiceResponse addItem(final ItemIdRegistry itemIdRegistry) {
		List<String> items = itemIdRegistry.itemIds().stream()
			.map(itemReader::read)
			.map(Item::getName)
			.toList();

		for (String itemName : items) {
			itemRanking.increasePoint(itemName, 1);
		}

		Long memberId = memberUtils.getCurrentMemberId();
		List<Long> memberItemIds = memberItemAppender.addMemberItems(itemIdRegistry.itemIds(), memberId);

		return new ItemAddServiceResponse(memberItemIds);
	}

	public ItemGetServiceResponse getItem(final Long itemId) {
		boolean isMemberItem;

		Item item = itemReader.read(itemId);
		Long memberId = memberUtils.getCurrentMemberId();
		isMemberItem = memberItemChecker.existMemberItemByMemberId(memberId, item);

		Double itemAvgRating = reviewStatistics.getReviewAvgByItemId(itemId);
		ItemInfo itemInfo = ItemInfo.from(item);

		boolean isReviewed = reviewReader.existsReviewByMemberIdAndItemId(memberId, itemId);

		return ItemGetServiceResponse.builder()
			.itemInfo(itemInfo)
			.isMemberItem(isMemberItem)
			.itemUrl(item.getUrl())
			.itemAvgRate(itemAvgRating)
			.isReviewed(isReviewed)
			.build();
	}

	public void removeMemberItems(final ItemRemovalList itemRemovalList) {
		Long memberId = memberUtils.getCurrentMemberId();

		for (Long itemId : itemRemovalList.itemIds()) {
			MemberItem memberItem = memberItemReader.read(itemId, memberId);
			memberItemRemover.remove(memberItem.getId());
		}
	}

	public ItemGetNamesServiceResponse getItemNamesByKeyword(final String keyword) {
		List<ItemInfo> itemNameGetResults = itemFinder.getItemNamesByKeyword(keyword);

		return new ItemGetNamesServiceResponse(itemNameGetResults);
	}

	public ItemGetByCursorServiceResponse getItemsByCursor(
		final String keyword,
		final CursorPageParameters parameters
	) {
		CursorSummary<ItemCursorSummary> itemCursorSummaryCursorSummary = itemCursorReader.readByCursor(
			keyword,
			parameters
		);

		int itemTotalCount = itemReader.getItemTotalCountByKeyword(keyword);

		return new ItemGetByCursorServiceResponse(
			itemTotalCount,
			itemCursorSummaryCursorSummary
		);
	}

	public MemberItemGetServiceResponse getMemberItemsByCursor(
		final Hobby hobby,
		final Long folderId,
		final CursorPageParameters parameters
	) {
		Long memberId = memberUtils.getCurrentMemberId();
		int totalMemberItemCount = memberItemReader.countByMemberIdAndHobby(memberId, hobby);

		CursorSummary<MemberItemSummary> cursorSummary = memberItemReader.readMemberItem(
			hobby,
			folderId,
			memberId,
			parameters
		);

		return new MemberItemGetServiceResponse(cursorSummary, totalMemberItemCount);
	}

	public MemberItemFolderGetServiceResponse getMemberItemFolderByCursor(
			final Hobby hobby,
			final CursorPageParameters parameters
	) {
		Long memberId = memberUtils.getCurrentMemberId();
		int totalMemberItemFolderCount = memberItemFolderReader.countByMemberIdAndHobby(memberId, hobby);

		CursorSummary<MemberItemFolderCursorSummary> cursorSummary = memberItemFolderReader.readMemberItemFolderByCursor(
			hobby,
			memberId,
			parameters
		);

		return new MemberItemFolderGetServiceResponse(cursorSummary, totalMemberItemFolderCount);
	}


	public List<ItemRankingServiceResponse> getRanking() {
		return itemRanking.viewRanking();
	}

	public void createMemberItemFolder(final String folderName, final Hobby hobby) {
		Long memberId = memberUtils.getCurrentMemberId();
		memberItemFolderAppender.append(folderName, memberId, hobby);
	}

	public void modifyMemberItemFolder(final Long folderId, final String folderName) {
		Long memberId = memberUtils.getCurrentMemberId();

		memberItemFolderValidator.validateExsitMemberItemFolder(folderId, memberId);

		memberItemFolderModifier.modify(folderId, folderName);
	}
}
