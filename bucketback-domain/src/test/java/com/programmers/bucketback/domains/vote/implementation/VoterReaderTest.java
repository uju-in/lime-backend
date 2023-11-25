package com.programmers.bucketback.domains.vote.implementation;

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

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.VoteBuilder;
import com.programmers.bucketback.domains.vote.domain.Voter;
import com.programmers.bucketback.domains.vote.domain.VoterBuilder;
import com.programmers.bucketback.domains.vote.repository.VoterRepository;

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
			final Long returnedItemId = voterReader.readItemId(vote, memberId);

			// then
			assertThat(returnedItemId).isEqualTo(voter.getItemId());
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
			final Long returnedItemId = voterReader.readItemId(vote, memberId);

			// then
			assertThat(returnedItemId).isNull();
		}
	}

	@Nested
	@DisplayName("투표자 조회 테스트")
	class readTest {
		@Test
		@DisplayName("투표와 회원 id가 일치하는 투표자가 있다면 기존의 투표자를 반환한다.")
		void readExistingVoterTest() {
			// given
			final Vote vote = VoteBuilder.build();
			final Long memberId = 1L;
			final Long itemId = 1L;
			final Voter voter = VoterBuilder.build(vote, memberId, itemId);

			given(voterRepository.findByVoteAndMemberId(any(Vote.class), anyLong()))
				.willReturn(Optional.of(voter));

			// when
			final Voter returnedVoter = voterReader.read(vote, memberId, itemId);

			// then
			assertThat(returnedVoter).usingRecursiveComparison()
				.isEqualTo(voter);
		}

		@Test
		@DisplayName("투표와 회원 id가 일치하는 투표자가 없다면 새 투표자를 반환한다.")
		void readNewVoterTest() {
			// given
			final Vote vote = VoteBuilder.build();
			final Long memberId = 1L;
			final Long itemId = 1L;
			final Voter voter = VoterBuilder.build(vote, memberId, itemId);

			given(voterRepository.findByVoteAndMemberId(any(Vote.class), anyLong()))
				.willReturn(Optional.empty());

			// when
			final Voter returnedVoter = voterReader.read(vote, memberId, itemId);

			// then
			assertThat(returnedVoter).usingRecursiveComparison()
				.ignoringFields("id")
				.isEqualTo(voter);
		}
	}
}
