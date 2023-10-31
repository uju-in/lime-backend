package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.Voter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteManager {

	private final VoterAppender voterAppender;

	@Transactional
	public void vote(
		final Vote vote,
		final Long memberId,
		final Long itemId
	) {
		final Voter voter = voterAppender.append(vote, memberId, itemId);
		vote.addVoter(voter);
	}
}
