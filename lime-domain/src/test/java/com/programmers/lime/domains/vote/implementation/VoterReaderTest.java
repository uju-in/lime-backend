package com.programmers.lime.domains.vote.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

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
import com.programmers.lime.error.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class VoterReaderTest {

	@InjectMocks
	private VoterReader voterReader;

	@Mock
	private VoterRepository voterRepository;

	@Nested
	@DisplayName("투표자가 선택한 아이템 id 반환 테스트")
	class ReadItemIdTest {
		@Test
		@DisplayName("투표와 회원 id가 일치하는 투표자가 있다면 투표자가 선택한 아이템 id를 반환한다.")
		void readVoterSelectedItemIdTest() {
			// given
			final Vote vote = VoteBuilder.build();
			final Long memberId = 1L;
			final Long itemId = 1L;
			final Voter voter = VoterBuilder.build(vote, memberId, itemId);

			given(voterRepository.findByVoteAndMemberId(any(Vote.class), anyLong()))
				.willReturn(Optional.of(voter));

			// when
			final Long result = voterReader.readItemId(vote, memberId);

			// then
			assertThat(result).isEqualTo(voter.getItemId());
		}

		@Test
		@DisplayName("투표와 회원 id가 일치하는 투표자가 없다면 null을 반환한다.")
		void readNullItemIdTest() {
			// given
			final Vote vote = VoteBuilder.build();
			final Long memberId = 1L;

			given(voterRepository.findByVoteAndMemberId(any(Vote.class), anyLong()))
				.willReturn(Optional.empty());

			// when
			final Long result = voterReader.readItemId(vote, memberId);

			// then
			assertThat(result).isNull();
		}
	}

	@Test
	@DisplayName("투표와 회원 id가 일치하는 투표자가 있다면 Optional로 투표자를 반환한다.")
	void findTest() {
		// given
		final Vote vote = VoteBuilder.build();
		final Long memberId = 1L;
		final Voter voter = VoterBuilder.build(vote, memberId, 1L);

		given(voterRepository.findByVoteAndMemberId(any(Vote.class), anyLong()))
			.willReturn(Optional.of(voter));

		// when
		final Optional<Voter> result = voterReader.find(vote, memberId);

		// then
		assertThat(result).isEqualTo(Optional.of(voter));
	}

	@Nested
	@DisplayName("투표자 조회 테스트")
	class ReadTest {
		@Test
		@DisplayName("투표와 회원 id가 일치하는 투표자가 있다면 기존의 투표자를 반환한다.")
		void readExistingVoterTest() {
			// given
			final Vote vote = VoteBuilder.build();
			final Long memberId = 1L;
			final Voter voter = VoterBuilder.build(vote, memberId, 1L);

			given(voterRepository.findByVoteAndMemberId(any(Vote.class), anyLong()))
				.willReturn(Optional.of(voter));

			// when
			final Voter result = voterReader.read(vote, memberId);

			// then
			assertThat(result).usingRecursiveComparison()
				.isEqualTo(voter);
		}

		@Test
		@DisplayName("투표와 회원 id가 일치하는 투표자가 없다면 예외가 발생한다.")
		void occurExceptionIfNoVoterTest() {
			// given
			final Vote vote = VoteBuilder.build();
			final Long memberId = 1L;

			given(voterRepository.findByVoteAndMemberId(any(Vote.class), anyLong()))
				.willReturn(Optional.empty());

			// when & then
			assertThatThrownBy(
				() -> voterReader.read(vote, memberId)
			).isInstanceOf(EntityNotFoundException.class);
		}
	}
}
