package com.programmers.lime.redis.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.redis.dto.ItemRankingServiceResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemRanking {

	private final RedisTemplate redisTemplate;

	@Transactional
	public void increasePoint(
		final String itemName,
		final int score
	) {
		redisTemplate.opsForZSet().incrementScore("itemRanking", itemName, score);
	}

	@Transactional
	public void addRanking(
		final String itemName
	) {
		redisTemplate.opsForZSet().add("itemRanking", itemName, 0);
	}

	@Transactional(readOnly = true)
	public List<ItemRankingServiceResponse> viewRanking() {
		String key = "itemRanking";
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
		Set<ZSetOperations.TypedTuple<String>> typedTuples =
			zSetOperations.reverseRangeWithScores(key, 0, 10);

		List<ItemRankingServiceResponse> response = new ArrayList<>();
		int rank = 1;

		for (ZSetOperations.TypedTuple<String> tuple : typedTuples) {
			response.add(ItemRankingServiceResponse.from(tuple, rank++));
		}

		return response;
	}

}
