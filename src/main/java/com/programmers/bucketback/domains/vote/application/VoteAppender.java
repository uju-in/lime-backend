package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.vote.application.dto.request.CreateVoteServiceRequest;
import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteAppender {

	private final VoteRepository voteRepository;

	@Transactional
	public Vote append(
		final Long memberId,
		final CreateVoteServiceRequest request
	) {
		final Vote vote = Vote.builder()
			.memberId(memberId)
			.item1Id(request.item1Id())
			.item2Id(request.item2Id())
			.hobby(request.hobby())
			.content(request.content())
			.build();

		return voteRepository.save(vote);
	}
}
