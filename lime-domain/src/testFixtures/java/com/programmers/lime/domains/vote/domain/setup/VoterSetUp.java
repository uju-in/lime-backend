package com.programmers.lime.domains.vote.domain.setup;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.Voter;
import com.programmers.lime.domains.vote.repository.VoterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoterSetUp {

	private final VoterRepository voterRepository;

	public Voter saveOne(
		final Vote vote,
		final Long memberId,
		final Long itemId
	) {
		final Voter voter = new Voter(vote, memberId, itemId);

		return voterRepository.save(voter);

	}
}
