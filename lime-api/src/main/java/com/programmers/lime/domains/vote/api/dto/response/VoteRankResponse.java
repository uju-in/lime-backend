package com.programmers.lime.domains.vote.api.dto.response;

import java.util.List;

import com.programmers.lime.redis.vote.VoteRankingInfo;

public record VoteRankResponse(
	List<VoteRankingInfo> rankingInfos
) {
	public static VoteRankResponse from(final List<VoteRankingInfo> rankingInfos) {
		return new VoteRankResponse(rankingInfos);
	}
}
