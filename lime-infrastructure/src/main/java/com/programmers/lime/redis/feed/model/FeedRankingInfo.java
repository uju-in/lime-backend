package com.programmers.lime.redis.feed.model;

import org.springframework.data.redis.core.ZSetOperations;

import lombok.Builder;

@Builder
public record FeedRankingInfo(

	Long feedId,
	String hobbyName,
	String feedContent,
	int likeCount

) {
	public static FeedRankingInfo of(
		final ZSetOperations.TypedTuple<Object> objectTypedTuple
	) {
		FeedRankingInfo response = (FeedRankingInfo)objectTypedTuple.getValue();

		return FeedRankingInfo.builder()
			.feedId(response.feedId())
			.hobbyName(response.hobbyName())
			.feedContent(response.feedContent())
			.likeCount(response.likeCount())
			.build();
	}
}
