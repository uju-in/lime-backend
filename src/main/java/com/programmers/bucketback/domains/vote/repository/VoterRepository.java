package com.programmers.bucketback.domains.vote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.Voter;

public interface VoterRepository extends JpaRepository<Voter, Long> {
	int countByVoteAndItemId(
		final Vote vote,
		final Long itemId
	);

	Optional<Voter> findByVoteAndMemberId(
		final Vote vote,
		final Long memberId
	);

	boolean existsByVoteAndMemberId(
		final Vote vote,
		final Long memberId
	);

	void deleteByVoteAndMemberId(
		final Vote vote,
		final Long memberId
	);
}
