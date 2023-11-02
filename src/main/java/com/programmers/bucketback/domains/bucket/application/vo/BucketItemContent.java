package com.programmers.bucketback.domains.bucket.application.vo;

import com.programmers.bucketback.domains.item.domain.Item;

import lombok.Builder;

@Builder
public record BucketItemContent(
	/** refactor : itemContent와 같은 아이템 정보가 담긴 객체로 반환한다. */
	Long itemId,
	String itemName,
	Integer itemPrice,
	String itemImgUrl
) {
	public static BucketItemContent from(final Item item){
		return BucketItemContent.builder()
			.itemId(item.getId())
			.itemName(item.getName())
			.itemPrice(item.getPrice())
			.itemImgUrl(item.getImage())
			.build();
	}
}
