package com.programmers.lime.domains.vote.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.VoteBuilder;
import com.programmers.lime.domains.vote.domain.Voter;
import com.programmers.lime.domains.vote.domain.VoterBuilder;
import com.programmers.lime.domains.vote.repository.VoterRepository;

@ExtendWith(MockitoExtension.class)
class VoteManagerTest {

	@InjectMocks
	private VoteManager voteManager;

	@Mock
	private VoterReader voterReader;

	@Mock
	private VoterRepository voterRepository;

	@Test
	@DisplayName("투표를 취소한다.")
	void cancelTest() {
		// given
		final Vote vote = VoteBuilder.build();
		final Long memberId = 1L;
		final Voter voter = VoterBuilder.build(vote, memberId, 1L);

		vote.addVoter(voter);

		will(invocation -> {
			final Vote givenVote = invocation.getArgument(0);
			final Long givenMemberId = invocation.getArgument(1);
			givenVote.getVoters().removeIf(savedVoter -> savedVoter.getMemberId().equals(givenMemberId));
			return null;
		}).given(voterRepository).deleteByVoteAndMemberId(any(Vote.class), anyLong());

		// when
		voteManager.cancel(vote, memberId);

		// then
		assertThat(vote.getVoters()).doesNotContain(voter);
	}

	@Nested
	@DisplayName("투표에")
	class ParticipateTest {

		@Test
		@DisplayName("처음 참여한다.")
		void participateFirstTest() {
			// given
			final Vote vote = VoteBuilder.build();
			final Long memberId = 1L;
			final Long itemId = 1L;
			final Voter voter = VoterBuilder.build(vote, memberId, itemId);

			given(voterReader.read(any(Vote.class), anyLong(), anyLong()))
				.willReturn(voter);

			// when
			voteManager.participate(vote, memberId, itemId);

			// then
			assertThat(voter.getVote()).isEqualTo(vote);
			assertThat(voter.getMemberId()).isEqualTo(memberId);
			assertThat(voter.getItemId()).isEqualTo(itemId);
			assertThat(vote.getVoters()).containsOnly(voter);
		}

		@Test
		@DisplayName("재참여한다.")
		void reParticipateTest() {
			// given
			final Vote vote = VoteBuilder.build();
			final Long memberId = 1L;
			final Long originSelectedItemId = 1L;
			final Long newSelectedItemId = 2L;
			final Voter voter = VoterBuilder.build(vote, memberId, originSelectedItemId);

			vote.addVoter(voter);

			given(voterReader.read(any(Vote.class), anyLong(), anyLong()))
				.willReturn(voter);

			// when
			voteManager.participate(vote, memberId, newSelectedItemId);

			// then
			assertThat(voter.getVote()).isEqualTo(vote);
			assertThat(voter.getMemberId()).isEqualTo(memberId);
			assertThat(voter.getItemId()).isEqualTo(newSelectedItemId);
			assertThat(vote.getVoters()).containsOnly(voter);
		}
	}
}
