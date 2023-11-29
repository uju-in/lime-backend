package com.programmers.bucketback.redis.feed;

import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import com.programmers.bucketback.redis.feed.model.FeedRankingInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedRedisManager {

	private static final String FEED_RANKING_INFO_SET_KEY = "feedRankingInfo";
	private final RedisTemplate<String, Object> redisTemplate;

	public void increasePopularity(final FeedRankingInfo feedRankingInfo) {
		redisTemplate.opsForZSet().incrementScore(FEED_RANKING_INFO_SET_KEY, feedRankingInfo, 1);
	}

	public List<FeedRankingInfo> getFeedRanking() {
		ZSetOperations<String, Object> ZSetOperations = redisTemplate.opsForZSet();

		Set<ZSetOperations.TypedTuple<Object>> feedRankingInfoSet =
			ZSetOperations.reverseRangeWithScores(
				FEED_RANKING_INFO_SET_KEY, 0, 9
			);

		return feedRankingInfoSet.stream()
			.map(FeedRankingInfo::of)
			.toList();
	}

	public void increasePopularity(final Long targetFeedId) {
		ZSetOperations<String, Object> ZSetOperations = redisTemplate.opsForZSet();

		Set<ZSetOperations.TypedTuple<Object>> feedRankingInfoSet =
			ZSetOperations.reverseRangeWithScores(
				FEED_RANKING_INFO_SET_KEY, 0, 9
			);

		feedRankingInfoSet.stream()
			.filter(s -> {
				FeedRankingInfo feedRankingInfo = (FeedRankingInfo)s.getValue();
				Long feedId = feedRankingInfo.feedId();
				return feedId.equals(targetFeedId);
			})
			.findFirst()
			.ifPresent(feedRankingInfo -> {
				ZSetOperations.incrementScore(FEED_RANKING_INFO_SET_KEY, feedRankingInfo.getValue(), 1);
			});
	}
}
