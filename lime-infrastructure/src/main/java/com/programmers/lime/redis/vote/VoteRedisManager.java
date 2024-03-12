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

	public static final String KEY = "VOTE_";

	private final RedisTemplate<String, Object> redisTemplate;

	public void addRanking(
		final String hobby,
		final VoteRankingInfo rankingInfo
	) {
		redisTemplate.opsForZSet().add(KEY + hobby, rankingInfo, 0);
		redisTemplate.expire(KEY + hobby, 1, TimeUnit.DAYS);
	}

	public void increasePopularity(
		final String hobby,
		final VoteRankingInfo rankingInfo
	) {
		redisTemplate.opsForZSet().incrementScore(KEY + hobby, rankingInfo, 1);
	}

	public void decreasePopularity(
		final String hobby,
		final VoteRankingInfo rankingInfo
	) {
		redisTemplate.opsForZSet().incrementScore(KEY + hobby, rankingInfo, -1);
	}

	public List<VoteRankingInfo> getRanking(final String hobby) {
		final ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
		final Set<ZSetOperations.TypedTuple<Object>> typedTuples = zSetOperations.reverseRangeWithScores(KEY + hobby,
			0,
			5);

		if (typedTuples == null) {
			throw new RedisException("투표 랭킹을 찾을 수 없습니다.");
		}

		return typedTuples.stream()
			.map(VoteRankingInfo::from)
			.toList();
	}

	public void remove(
		final String hobby,
		final VoteRankingInfo rankingInfo
	) {
		redisTemplate.opsForZSet().remove(KEY + hobby, rankingInfo);
	}

	public void updateRanking(
		final String hobby,
		final boolean voting,
		final VoteRankingInfo rankingInfo
	) {
		if (voting) {
			increasePopularity(hobby, rankingInfo);
		} else {
			remove(hobby, rankingInfo);
		}
	}
}
