package com.programmers.lime.domains.feed.model;

import java.time.LocalDateTime;

import com.programmers.lime.domains.feed.domain.Feed;

import lombok.Builder;

@Builder
public record FeedInfo(
	Long id,
	String hobby,
	String content,
	int totalPrice,
	LocalDateTime createdAt,
	boolean hasAdoptedComment,
	int likeCount,
	Boolean isLiked
) {
	public static FeedInfo of(
		final Feed feed,
		final int totalPrice,
		final Boolean isLiked
	) {
		return FeedInfo.builder()
			.id(feed.getId())
			.hobby(feed.getHobby().getName())
			.content(feed.getFeedContent())
			.totalPrice(totalPrice)
			.createdAt(feed.getCreatedAt())
			.hasAdoptedComment(feed.hasAdoptedComment())
			.likeCount(feed.getLikes().size())
			.isLiked(isLiked)
			.build();
	}
}
