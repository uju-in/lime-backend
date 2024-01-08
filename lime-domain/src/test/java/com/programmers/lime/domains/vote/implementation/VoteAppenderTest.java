package com.programmers.lime.domains.vote.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.VoteBuilder;
import com.programmers.lime.domains.vote.model.VoteCreateImplRequest;
import com.programmers.lime.domains.vote.model.VoteCreateImplRequestBuilder;
import com.programmers.lime.domains.vote.repository.VoteRepository;

@ExtendWith(MockitoExtension.class)
class VoteAppenderTest {

	@InjectMocks
	private VoteAppender voteAppender;

	@Mock
	private VoteRepository voteRepository;

	@Test
	@DisplayName("투표를 저장한다.")
	void appendTest() {
		// given
		final Long memberId = 1L;
		final VoteCreateImplRequest request = VoteCreateImplRequestBuilder.build();
		final Vote vote = VoteBuilder.build(memberId);

		given(voteRepository.save(any(Vote.class)))
			.willReturn(vote);

		// when
		final Vote savedVote = voteAppender.append(memberId, request);

		// then
		assertThat(savedVote).usingRecursiveComparison()
			.isEqualTo(vote);
	}
}
