package com.programmers.lime.domains.vote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.vote.domain.Voter;

public interface VoterRepository extends JpaRepository<Voter, Long> {
	int countByVoteId(final Long voteId);

	int countByVoteIdAndItemId(
		final Long voteId,
		final Long itemId
	);

	Optional<Voter> findByVoteIdAndMemberId(
		final Long voteId,
		final Long memberId
	);

	void deleteByVoteId(final Long voteId);

	void deleteByVoteIdAndMemberId(
		final Long voteId,
		final Long memberId
	);
}
