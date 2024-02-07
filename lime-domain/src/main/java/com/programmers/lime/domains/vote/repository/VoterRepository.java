package com.programmers.lime.domains.vote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.Voter;

public interface VoterRepository extends JpaRepository<Voter, Long> {
	int countByVoteAndItemId(
		final Vote vote,
		final Long itemId
	);

	Optional<Voter> findByVoteAndMemberId(
		final Vote vote,
		final Long memberId
	);
}
