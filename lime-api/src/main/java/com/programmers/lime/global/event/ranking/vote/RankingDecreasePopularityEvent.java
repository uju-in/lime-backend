package com.programmers.lime.global.event.ranking.vote;

import com.programmers.lime.redis.vote.VoteRankingInfo;

public record RankingDecreasePopularityEvent(
	String hobby,
	VoteRankingInfo voteRankingInfo
) {
}
