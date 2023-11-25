package com.programmers.bucketback.domains.vote.implementation;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.VoteBuilder;
import com.programmers.bucketback.domains.vote.repository.VoteRepository;

@ExtendWith(MockitoExtension.class)
class VoteRemoverTest {

	@InjectMocks
	private VoteRemover voteRemover;

	@Mock
	private VoteRepository voteRepository;

	@Test
	@DisplayName("투표를 삭제한다.")
	void removeTest() {
		// given
		final Vote vote = VoteBuilder.build();

		willDoNothing()
			.given(voteRepository).delete(any(Vote.class));

		// when
		voteRemover.remove(vote);

		// then
		then(voteRepository).should().delete(vote);
	}
}
