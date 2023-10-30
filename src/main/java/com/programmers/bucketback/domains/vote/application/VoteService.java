package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.item.application.ItemReader;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.vote.application.dto.request.CreateVoteServiceRequest;
import com.programmers.bucketback.domains.vote.domain.Vote;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {

	private final VoteAppender voteAppender;
	private final ItemReader itemReader;

	public Long createVote(final CreateVoteServiceRequest request) {
		final Long memberId = MemberUtils.getCurrentMemberId();
		final Item optionItem1 = itemReader.read(request.option1ItemId());
		final Item optionItem2 = itemReader.read(request.option2ItemId());

		final Vote vote = voteAppender.append(memberId, optionItem1, optionItem2, request.hobby(), request.content());

		return vote.getId();
	}
}
