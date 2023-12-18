package com.programmers.bucketback.redis.vote;

import org.springframework.data.redis.core.ZSetOperations;

import lombok.Builder;

@Builder
public record VoteRedis(
	Long id,
	String content,
	String startTime,
	Long item1Id,
	String item1Name,
	Long item2Id,
	String item2Name,
	int participants
) {
	public static VoteRedis from(final ZSetOperations.TypedTuple<Object> typedTuple) {
		final Long id = ((VoteRedis) typedTuple.getValue()).id();
		final String content = ((VoteRedis) typedTuple.getValue()).content();
		final String startTime = ((VoteRedis) typedTuple.getValue()).startTime();
		final Long item1Id = ((VoteRedis) typedTuple.getValue()).item1Id();
		final String item1Name = ((VoteRedis) typedTuple.getValue()).item1Name();
		final Long item2Id = ((VoteRedis) typedTuple.getValue()).item2Id();
		final String item2Name = ((VoteRedis) typedTuple.getValue()).item2Name();
		final int participants = typedTuple.getScore().intValue();

		return VoteRedis.builder()
			.id(id)
			.content(content)
			.startTime(startTime)
			.item1Id(item1Id)
			.item1Name(item1Name)
			.item2Id(item2Id)
			.item2Name(item2Name)
			.participants(participants)
			.build();
	}
}
