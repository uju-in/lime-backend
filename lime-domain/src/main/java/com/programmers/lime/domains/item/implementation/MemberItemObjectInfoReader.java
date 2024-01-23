package com.programmers.lime.domains.item.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.common.model.ObjectType;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.MemberItem;
import com.programmers.lime.domains.item.model.MemberItemMetadata;
import com.programmers.lime.domains.item.model.MemberItemObjectInfo;
import com.programmers.lime.domains.item.model.MemberItemObjectMetadata;
import com.programmers.lime.domains.review.implementation.ReviewReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemObjectInfoReader implements IObjectReader {

	private final MemberItemReader memberItemReader;

	private final ReviewReader reviewReader;

	@Override
	public List<MemberItemObjectInfo> readObjects(final Long folderId, final Long memberId) {
		List<MemberItem> memberItems = memberItemReader.memberItemsByFolderIdAndMemberId(folderId, memberId);
		return memberItems.stream()
			.map(this::mapToMemberItemObjectInfo)
			.toList();
	}

	private MemberItemObjectInfo mapToMemberItemObjectInfo(final MemberItem memberItem) {
		return MemberItemObjectInfo.builder()
			.objectId(memberItem.getId())
			.originalName(memberItem.getItem().getName())
			.type(ObjectType.ITEM)
			.createdAt(memberItem.getCreatedAt())
			.modifiedAt(memberItem.getModifiedAt())
			.metadata(toMetadata(memberItem))
			.build();
	}

	private MemberItemObjectMetadata toMetadata(final MemberItem memberItem) {
		Item item = memberItem.getItem();
		int myItemCount = memberItemReader.countMyItem(item.getId());
		int reviewCount = reviewReader.countReviewByItemId(item.getId());
		int price = item.getPrice();

		return MemberItemObjectMetadata.builder().memberItemMetadata(
			MemberItemMetadata.builder()
				.itemId(item.getId())
				.hobby(item.getHobby())
				.itemUrl(item.getUrl())
				.imageUrl(item.getImage())
				.myItemCount(myItemCount)
				.reviewCount(reviewCount)
				.price(price)
				.build()
		).build();
	}
}
