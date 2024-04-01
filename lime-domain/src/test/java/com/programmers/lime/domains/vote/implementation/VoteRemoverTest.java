package com.programmers.lime.domains.vote.implementation;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.VoteBuilder;
import com.programmers.lime.domains.vote.repository.VoteRepository;
import com.programmers.lime.domains.vote.repository.VoterRepository;

@ExtendWith(MockitoExtension.class)
class VoteRemoverTest {

	@InjectMocks
	private VoteRemover voteRemover;

	@Mock
	private VoteRepository voteRepository;

	@Mock
	private VoterRepository voterRepository;

	@Test
	@DisplayName("투표를 삭제한다.")
	void removeTest() {
		// given
		final Vote vote = VoteBuilder.build();

		willDoNothing()
			.given(voteRepository).delete(any(Vote.class));

		willDoNothing()
			.given(voterRepository).deleteByVoteId(anyLong());

		// when
		voteRemover.remove(vote);

		// then
		then(voteRepository).should().delete(vote);
	}
}
