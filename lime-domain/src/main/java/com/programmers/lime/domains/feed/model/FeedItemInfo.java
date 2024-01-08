package com.programmers.lime.domains.feed.model;

import com.programmers.lime.domains.feed.domain.FeedItem;

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
			.id(feedItem.getItem().getId())
			.name(feedItem.getItem().getName())
			.price(feedItem.getItem().getPrice())
			.image(feedItem.getItem().getImage())
			.build();
	}
}
