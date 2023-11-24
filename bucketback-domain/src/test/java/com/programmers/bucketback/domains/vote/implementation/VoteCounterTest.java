package com.programmers.bucketback.domains.vote.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.VoteBuilder;
import com.programmers.bucketback.domains.vote.domain.Voter;
import com.programmers.bucketback.domains.vote.domain.VoterBuilder;
import com.programmers.bucketback.domains.vote.repository.VoterRepository;

@ExtendWith(MockitoExtension.class)
class VoteCounterTest {

	@InjectMocks
	private VoteCounter voteCounter;

	@Mock
	private VoterRepository voterRepository;

	@Test
	@DisplayName("투표에서 아이템에 투표한 투표자의 수를 센다.")
	void countTest() {
		// given
		final int voterSize = 3;
		final Vote vote = VoteBuilder.build();
		final Long itemId = 1L;
		final List<Voter> voters = VoterBuilder.buildMany(voterSize, vote, itemId);

		given(voterRepository.countByVoteAndItemId(any(Vote.class), anyLong()))
			.willReturn(voterSize);

		// when
		final int count = voteCounter.count(vote, itemId);

		// then
		assertThat(count).isEqualTo(voters.size());
	}
}