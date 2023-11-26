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

import com.programmers.bucketback.domains.item.implementation.ItemReader;
import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.VoteBuilder;
import com.programmers.bucketback.domains.vote.repository.VoteRepository;
import com.programmers.bucketback.error.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class VoteReaderTest {

	@InjectMocks
	private VoteReader voteReader;

	@Mock
	private VoteCounter voteCounter;

	@Mock
	private VoteRepository voteRepository;

	@Mock
	private VoterReader voterReader;

	@Mock
	private ItemReader itemReader;

	@Nested
	@DisplayName("투표 엔티티 조회 테스트")
	class ReadTest {
		@Test
		@DisplayName("voteId와 일치하는 투표를 반환한다.")
		void readVoteTest() {
			// given
			final Vote vote = VoteBuilder.build();

			given(voteRepository.findById(anyLong()))
				.willReturn(Optional.of(vote));

			// when
			final Vote readVote = voteReader.read(vote.getId());

			// then
			assertThat(readVote).usingRecursiveComparison()
				.isEqualTo(vote);
		}

		@Test
		@DisplayName("voteId와 일치하는 투표가 없으면 예외가 발생한다.")
		void occurExceptionIfNoVoteTest() {
			// given
			final Long wrongVoteId = 0L;

			given(voteRepository.findById(anyLong()))
				.willReturn(Optional.empty());

			// when & then
			assertThatThrownBy(
				() -> voteReader.read(wrongVoteId)
			).isInstanceOf(EntityNotFoundException.class);
		}
	}
}