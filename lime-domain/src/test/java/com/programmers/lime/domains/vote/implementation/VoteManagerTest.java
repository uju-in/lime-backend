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
			final Long itemId = vote.getItem1Id();

			given(voterRepository.save(any(Voter.class)))
				.willReturn(VoterBuilder.build(vote.getId(), memberId, itemId));

			given(voterReader.count(vote.getId()))
				.willReturn(vote.getMaximumParticipants() - 1);

			// when
			voteManager.participate(vote, memberId, itemId);

			// then
			assertThat(vote.isVoting()).isTrue();

			// verify
			then(voterRepository).should().save(any(Voter.class));
		}

		@Test
		@DisplayName("투표에 참여하고 참여자가 최대인원이 되면 투표를 종료한다.")
		void participateAndCloseTest() {
			// given
			final Vote vote = VoteBuilder.build();
			final Long memberId = 1L;
			final Long itemId = vote.getItem1Id();

			given(voterRepository.save(any(Voter.class)))
				.willReturn(VoterBuilder.build(vote.getId(), memberId, itemId));

			given(voterReader.count(vote.getId()))
				.willReturn(vote.getMaximumParticipants());

			// when
			voteManager.participate(vote, memberId, itemId);

			// then
			assertThat(vote.isVoting()).isFalse();

			// verify
			then(voterRepository).should().save(any(Voter.class));
		}
	}

	@Test
	@DisplayName("재참여한다.")
	void reParticipateTest() {
		// given
		final Long originSelectedItemId = 1L;
		final Long newSelectedItemId = 2L;
		final Voter voter = VoterBuilder.build(1L, 1L, originSelectedItemId);

		// when
		voteManager.reParticipate(newSelectedItemId, voter);

		// then
		assertThat(voter.getItemId()).isEqualTo(newSelectedItemId);
	}

	@Test
	@DisplayName("투표를 취소한다.")
	void cancelTest() {
		// given
		final Long voteId = 1L;
		final Long memberId = 1L;

		doNothing()
			.when(voterRepository).deleteByVoteIdAndMemberId(voteId, memberId);

		// when
		voteManager.cancel(voteId, memberId);

		// then
		then(voterRepository).should().deleteByVoteIdAndMemberId(voteId, memberId);
	}
}
