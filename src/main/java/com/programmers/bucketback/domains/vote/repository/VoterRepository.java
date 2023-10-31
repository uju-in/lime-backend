package com.programmers.bucketback.domains.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.vote.domain.Voter;

public interface VoterRepository extends JpaRepository<Voter, Long> {
}
