package com.programmers.bucketback.domains.feed.model;

import com.programmers.bucketback.domains.feed.domain.FeedItem;

import lombok.Builder;

@Builder
public record FeedItemInfo(
	Long id,
	String name,
	int price,
	String image
) {
	public static FeedItemInfo from(final FeedItem feedItem) {
		return FeedItemInfo.builder()
			.id(feedItem.getId())
			.name(feedItem.getItem().getName())
			.price(feedItem.getItem().getPrice())
			.image(feedItem.getItem().getImage())
			.build();
	}
}
