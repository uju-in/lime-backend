package com.programmers.lime.redis.dto;

import org.springframework.data.redis.core.ZSetOperations;

public record ItemRankingServiceResponse(
	int rank,
	String itemName,
	int score
) {
	public static ItemRankingServiceResponse from(
		final ZSetOperations.TypedTuple<String> tuple,
		final int rank
	) {
		return new ItemRankingServiceResponse(rank, tuple.getValue(), tuple.getScore().intValue());
	}
}
