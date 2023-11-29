package com.programmers.bucketback.redis.feed;

import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import com.programmers.bucketback.redis.feed.model.FeedRankingInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedRedisManager {

	private static final String FEED_RANKING_INFO_HASH_KEY = "feedRankingInfoHashKey";
	private static final String FEED_RANKING_INFO_SET_KEY = "feedRankingInfoSetKey";

	private final RedisTemplate<String, Object> redisTemplate;

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

	public boolean isFeedExist(final Long feedId) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();

		return hash.hasKey(FEED_RANKING_INFO_HASH_KEY, feedId);
	}

	public void changePopularity(final FeedRankingInfo feedRankingInfo, final int value) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		hash.put(FEED_RANKING_INFO_HASH_KEY, feedRankingInfo.feedId(), feedRankingInfo);

		redisTemplate.opsForZSet().incrementScore(FEED_RANKING_INFO_SET_KEY, feedRankingInfo, value);
	}

	public void changePopularity(final Long feedId, final int value) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();

		if (!hash.hasKey(FEED_RANKING_INFO_HASH_KEY, feedId)) {
			return;
		}

		FeedRankingInfo feedRankingInfo = (FeedRankingInfo)hash.get(FEED_RANKING_INFO_HASH_KEY, feedId);
		changePopularity(feedRankingInfo, value);
	}
}
