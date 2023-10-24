package com.programmers.bucketback.domains.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.vote.domain.Vote;

public interface VoteReposiory extends JpaRepository<Vote, Long> {
}
