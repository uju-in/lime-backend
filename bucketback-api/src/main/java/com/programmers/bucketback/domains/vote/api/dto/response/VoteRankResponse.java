package com.programmers.bucketback.domains.vote.api.dto.response;

import java.util.List;

import com.programmers.bucketback.redis.vote.VoteRedis;

public record VoteRankResponse(
	List<VoteRedis> voteRanking
) {
	public static VoteRankResponse from(final List<VoteRedis> voteRanking) {
		return new VoteRankResponse(voteRanking);
	}
}
