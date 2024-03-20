package com.programmers.lime.global.event.ranking.vote;

import com.programmers.lime.redis.vote.VoteRankingInfo;

public record RankingDeleteEvent(
	String hobby,
	VoteRankingInfo voteRankingInfo
) {
}
