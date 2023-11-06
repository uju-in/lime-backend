package com.programmers.bucketback.domains.bucket.application.vo;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.item.application.vo.ItemInfo;

import lombok.Builder;

@Builder
public record BucketMemberItemCursorSummary(
	String cursorId,
	ItemInfo itemInfo,
	boolean isSelected,
	LocalDateTime createdAt
) {
	public static BucketMemberItemCursorSummary of(
		final String cursorId,
		final BucketMemberItemSummary bucketMemberItemSummary
	) {
		return BucketMemberItemCursorSummary.builder()
			.cursorId(cursorId)
			.itemInfo(ItemInfo.builder()
				.id(bucketMemberItemSummary.getItemId())
				.name(bucketMemberItemSummary.getName())
				.price(bucketMemberItemSummary.getPrice())
				.image(bucketMemberItemSummary.getImage())
				.build())
			.isSelected(bucketMemberItemSummary.isSelected())
			.createdAt(bucketMemberItemSummary.getCreatedAt())
			.build();
	}

}
