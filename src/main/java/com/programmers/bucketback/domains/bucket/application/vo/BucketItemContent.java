package com.programmers.bucketback.domains.bucket.application.vo;

import com.programmers.bucketback.domains.item.domain.Item;

import lombok.Builder;

@Builder
public record BucketItemContent(
	/** refactor : itemContent와 같은 아이템 정보가 담긴 객체로 반환한다. */
	Long id,
	String name,
	Integer price,
	String imgUrl
) {
	public static BucketItemContent from(final Item item){
		return BucketItemContent.builder()
			.id(item.getId())
			.name(item.getName())
			.price(item.getPrice())
			.imgUrl(item.getImage())
			.build();
	}
}
