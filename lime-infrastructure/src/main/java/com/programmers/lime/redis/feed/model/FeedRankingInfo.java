package com.programmers.lime.redis.feed.model;

import org.springframework.data.redis.core.ZSetOperations;

import lombok.Builder;

@Builder
public record FeedRankingInfo(

	Long feedId,
	String hobbyName,
	String feedContent,
	String bucketName,
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
			.bucketName(response.bucketName())
			.likeCount(response.likeCount())
			.build();
	}
}
