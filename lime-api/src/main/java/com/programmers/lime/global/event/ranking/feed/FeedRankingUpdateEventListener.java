package com.programmers.lime.global.event.ranking.feed;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.feed.FeedRedisManager;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedRankingUpdateEventListener {

	private final FeedRedisManager feedRedisManager;

	@Async
	@EventListener
	public void updateFeedRanking(final FeedRankingUpdateEvent event) {
		feedRedisManager.changePopularity(event.feedRankingInfo(), event.value());
	}
}
