package com.programmers.lime.domains.vote.application;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.implementation.VoteManager;
import com.programmers.lime.redis.vote.VoteRedisManager;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteLockManager {

	private final VoteManager voteManager;
	private final VoteRedisManager voteRedisManager;

	public void participate(
		final Vote vote,
		final Long memberId,
		final Long itemId
	) throws InterruptedException
	{
		while (Boolean.FALSE.equals(voteRedisManager.lock(String.valueOf(vote.getId())))) {
			Thread.sleep(10);
		}

		try {
			voteManager.participate(vote, memberId, itemId);
		} finally {
			voteRedisManager.unlock(String.valueOf(vote.getId()));
		}
	}
}
