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

	@Nested
	@DisplayName("투표 참여 테스트")
	class ParticipateTest {
		@Test
		@DisplayName("투표에 참여한다.")
		void participateTest() {
			// given
			final Vote vote = VoteBuilder.build();
			final Long memberId = 1L;
			final Long itemId = 1L;

			// when
			voteManager.participate(vote, memberId, itemId);

			// then
			assertThat(vote.getVoters()).hasSize(1);
			assertThat(vote.isVoting()).isTrue();
		}

		@Test
		@DisplayName("투표에 참여하고 참여자가 최대인원이 되면 투표를 종료한다.")
		void participateAndCloseTest() {
			// given
			final Vote vote = VoteBuilder.build();
			final Long memberId = 1L;
			final Long itemId = 1L;

			vote.addVoter(VoterBuilder.build(vote, 2L, 1L));
			vote.addVoter(VoterBuilder.build(vote, 3L, 2L));

			// when
			voteManager.participate(vote, memberId, itemId);

			// then
			assertThat(vote.getVoters()).hasSize(3);
			assertThat(vote.isVoting()).isFalse();
		}
	}

	@Test
	@DisplayName("재참여한다.")
	void reParticipateTest() {
		// given
		final Vote vote = VoteBuilder.build();
		final Long originSelectedItemId = 1L;
		final Long newSelectedItemId = 2L;
		final Voter voter = VoterBuilder.build(vote, 1L, originSelectedItemId);

		// when
		voteManager.reParticipate(newSelectedItemId, voter);

		// then
		assertThat(voter.getItemId()).isEqualTo(newSelectedItemId);
	}

	@Test
	@DisplayName("투표를 취소한다.")
	void cancelTest() {
		// given
		final Vote vote = VoteBuilder.build();
		final Long memberId = 1L;
		final Voter voter = VoterBuilder.build(vote, memberId, 1L);

		vote.addVoter(voter);

		given(voterReader.read(any(Vote.class), anyLong()))
			.willReturn(voter);
		doNothing().when(voterRepository).delete(voter);

		// when
		voteManager.cancel(vote, memberId);

		// then
		then(voterRepository).should().delete(voter);
	}
}
