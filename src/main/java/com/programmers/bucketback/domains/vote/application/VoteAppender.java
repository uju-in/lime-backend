package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.vote.application.dto.request.CreateVoteServiceRequest;
import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.repository.VoteReposiory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteAppender {

	private final VoteReposiory voteReposiory;

	@Transactional
	public Vote append(
		final Long memberId,
		final CreateVoteServiceRequest request
	) {
		final Vote vote = Vote.builder()
			.memberId(memberId)
			.option1ItemId(request.option1ItemId())
			.option2ItemId(request.option2ItemId())
			.hobby(request.hobby())
			.content(request.content())
			.build();

		return voteReposiory.save(vote);
	}
}
