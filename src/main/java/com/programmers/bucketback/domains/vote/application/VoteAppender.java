package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.item.domain.Item;
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
		final Item optionItem1,
		final Item optionItem2,
		final Hobby hobby,
		final String content
	) {
		Vote vote = Vote.builder()
			.memberId(memberId)
			.option1Item(optionItem1)
			.option2Item(optionItem2)
			.hobby(hobby)
			.content(content)
			.build();

		return voteReposiory.save(vote);
	}
}
