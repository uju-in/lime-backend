package com.programmers.lime.redis.vote;

import org.springframework.data.redis.core.ZSetOperations;

import lombok.Builder;

@Builder
public record VoteRedis(
	Long id,
	String item1Image,
	String item2Image,
	int participants
) {
	public static VoteRedis from(final ZSetOperations.TypedTuple<Object> typedTuple) {
		final Long id = ((VoteRedis)typedTuple.getValue()).id();
		final String item1Image = ((VoteRedis)typedTuple.getValue()).item1Image();
		final String item2Image = ((VoteRedis)typedTuple.getValue()).item2Image();
		final int participants = typedTuple.getScore().intValue();

		return VoteRedis.builder()
			.id(id)
			.item1Image(item1Image)
			.item2Image(item2Image)
			.participants(participants)
			.build();
	}
}
