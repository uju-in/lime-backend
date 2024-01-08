package com.programmers.lime.domains.vote.api.dto.response;

import java.util.List;

import com.programmers.lime.redis.vote.VoteRedis;

public record VoteRankResponse(
	List<VoteRedis> voteRanking
) {
	public static VoteRankResponse from(final List<VoteRedis> voteRanking) {
		return new VoteRankResponse(voteRanking);
	}
}
