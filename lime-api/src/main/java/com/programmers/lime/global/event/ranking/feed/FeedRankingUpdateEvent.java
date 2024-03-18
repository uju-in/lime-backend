package com.programmers.lime.global.event.ranking.feed;

import com.programmers.lime.redis.feed.model.FeedRankingInfo;

public record FeedRankingUpdateEvent(
	FeedRankingInfo feedRankingInfo,
	int value
) {
}
