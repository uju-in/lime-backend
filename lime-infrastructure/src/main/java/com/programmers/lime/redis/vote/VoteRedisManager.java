package com.programmers.lime.redis.vote;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import io.lettuce.core.RedisException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteRedisManager {

	public static final String KEY = "vote";

	private final RedisTemplate<String, Object> redisTemplate;

	public void addRanking(final VoteRedis rankingInfo) {
		redisTemplate.opsForZSet().add(KEY, rankingInfo, 0);
		redisTemplate.expire(KEY, 1, TimeUnit.DAYS);
	}

	public void increasePopularity(final VoteRedis rankingInfo) {
		redisTemplate.opsForZSet().incrementScore(KEY, rankingInfo, 1);
	}

	public List<VoteRedis> getRanking() {
		final ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
		final Set<ZSetOperations.TypedTuple<Object>> typedTuples = zSetOperations.reverseRangeWithScores(KEY, 0, 5);

		if (typedTuples == null) {
			throw new RedisException("투표 랭킹을 찾을 수 없습니다.");
		}

		return typedTuples.stream()
			.map(VoteRedis::from)
			.toList();
	}
}
