package com.programmers.lime.domains.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.vote.domain.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long>, VoteRepositoryForCursor {
}
