package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.repository.VoteReposiory;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteReader {

	private final VoteReposiory voteReposiory;

	@Transactional(readOnly = true)
	public Vote read(final Long voteId) {
		return voteReposiory.findById(voteId)
			.orElseThrow(() -> new BusinessException(ErrorCode.VOTE_NOT_FOUND));
	}
}
