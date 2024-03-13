package com.programmers.lime.global.event.ranking.vote;

import com.programmers.lime.redis.vote.VoteRankingInfo;

public record RankingAddEvent(
	String hobby,
	VoteRankingInfo voteRankingInfo
) {
}
