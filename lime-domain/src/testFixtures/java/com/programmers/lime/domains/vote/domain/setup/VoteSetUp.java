package com.programmers.lime.domains.vote.domain.setup;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.VoteBuilder;
import com.programmers.lime.domains.vote.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteSetUp {

	private final VoteRepository voteRepository;

	public Vote saveOne(
		final Long voteId,
		final Long item1Id,
		final Long item2Id
	) {
		final Vote vote = VoteBuilder.build(voteId, item1Id, item2Id);

		return voteRepository.save(vote);
	}

	public Vote save(final Vote vote) {
		return voteRepository.save(vote);
	}
}
