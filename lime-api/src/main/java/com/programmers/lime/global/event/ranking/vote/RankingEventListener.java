package com.programmers.lime.global.event.ranking.vote;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.vote.VoteRedisManager;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RankingEventListener {

	private final VoteRedisManager voteRedisManager;

	@Async
	@EventListener
	public void addRanking(final RankingAddEvent event) {
		voteRedisManager.addRanking(event.hobby(), event.voteRankingInfo());
	}

	@Async
	@EventListener
	public void updateRanking(final RankingUpdateEvent event) {
		voteRedisManager.updateRanking(event.hobby(), event.isVoting(), event.voteRankingInfo());
	}

	@Async
	@EventListener
	public void decreasePopularity(final RankingDecreasePopularityEvent event) {
		voteRedisManager.updatePopularity(event.hobby(), event.voteRankingInfo(), -1);
	}

	@Async
	@EventListener
	public void deleteRanking(final RankingDeleteEvent event) {
		voteRedisManager.deleteRanking(event.hobby(), event.voteRankingInfo());
	}
}
