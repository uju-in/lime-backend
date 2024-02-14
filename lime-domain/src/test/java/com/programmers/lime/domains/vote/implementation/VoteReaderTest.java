package com.programmers.lime.domains.vote.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorPageParametersBuilder;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.ItemBuilder;
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.domains.item.model.ItemInfo;
import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.VoteBuilder;
import com.programmers.lime.domains.vote.model.VoteCursorSummary;
import com.programmers.lime.domains.vote.model.VoteCursorSummaryBuilder;
import com.programmers.lime.domains.vote.model.VoteDetail;
import com.programmers.lime.domains.vote.model.VoteDetailInfo;
import com.programmers.lime.domains.vote.model.VoteSortCondition;
import com.programmers.lime.domains.vote.model.VoteStatusCondition;
import com.programmers.lime.domains.vote.model.VoteSummary;
import com.programmers.lime.domains.vote.model.VoteSummaryBuilder;
import com.programmers.lime.domains.vote.repository.VoteRepository;
import com.programmers.lime.error.EntityNotFoundException;

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

	@Test
	@DisplayName("투표 상세 조회한다.")
	void readDetailTest() {
		// given
		final Long memberId = 1L;
		final Vote vote = VoteBuilder.build();
		final Long item1Id = vote.getItem1Id();
		final Long item2Id = vote.getItem2Id();

		final Item item1 = ItemBuilder.build(item1Id);
		final Item item2 = ItemBuilder.build(item2Id);
		final ItemInfo item1Info = ItemInfo.from(item1);
		final ItemInfo item2Info = ItemInfo.from(item2);

		final int item1Votes = 4;
		final int item2Votes = 5;
		final VoteDetailInfo voteInfo = VoteDetailInfo.of(vote, item1Votes, item2Votes);

		given(voteRepository.findById(anyLong()))
			.willReturn(Optional.of(vote));
		given(itemReader.read(item1Id))
			.willReturn(item1);
		given(itemReader.read(item2Id))
			.willReturn(item2);
		given(voteCounter.count(any(Vote.class), eq(item1Id)))
			.willReturn(item1Votes);
		given(voteCounter.count(any(Vote.class), eq(item2Id)))
			.willReturn(item2Votes);
		given(voterReader.readItemId(any(Vote.class), anyLong()))
			.willReturn(item1Id);

		// when
		final VoteDetail result = voteReader.readDetail(vote.getId(), memberId);

		// then
		assertThat(result.voteInfo()).isEqualTo(voteInfo);
		assertThat(result.item1Info()).isEqualTo(item1Info);
		assertThat(result.item2Info()).isEqualTo(item2Info);
		assertThat(result.selectedItemId()).isEqualTo(item1Id);
	}

	@Test
	@DisplayName("검색 조건에 맞는 투표 목록을 조회한다.")
	void readByConditionTest() {
		// given
		final int DEFAULT_PAGING_SIZE = 20;
		final Hobby hobby = Hobby.BASKETBALL;
		final VoteStatusCondition statusCondition = null;
		final VoteSortCondition sortCondition = VoteSortCondition.RECENT;
		final CursorPageParameters parameters = CursorPageParametersBuilder.build();
		final Long memberId = 1L;

		final int pageSize = parameters.size() == null ? DEFAULT_PAGING_SIZE : parameters.size();
		final List<VoteCursorSummary> voteCursorSummaries = VoteCursorSummaryBuilder.buildMany(pageSize);
		final List<VoteSummary> voteSummaries = VoteSummaryBuilder.buildMany(voteCursorSummaries);
		final CursorSummary<VoteSummary> cursorSummary = CursorUtils.getCursorSummaries(voteSummaries);

		given(voteRepository.findAllByCursor(
			any(Hobby.class),
			eq(statusCondition),
			eq(sortCondition),
			eq(null),
			anyLong(),
			eq(parameters.cursorId()),
			eq(pageSize)
		)).willReturn(voteCursorSummaries);

		given(itemReader.read(anyLong()))
			.will(invocation -> {
				final Long givenItemId = invocation.getArgument(0);
				return ItemBuilder.build(givenItemId);
			});

		// when
		final CursorSummary<VoteSummary> result = voteReader.readByCursor(
			hobby,
			statusCondition,
			sortCondition,
			null,
			parameters,
			memberId
		);

		// then
		assertThat(result).isEqualTo(cursorSummary);
	}

	@Test
	@DisplayName("키워드가 투표 아이템 이름에 들어가는 투표의 개수를 센다.")
	void countByKeywordTest() {
		// given
		final String keyword = "농구공";
		final long count = 10;

		given(voteRepository.countByKeyword(anyString()))
			.willReturn(count);

		// when
		final long result = voteReader.countByKeyword(keyword);

		// then
		assertThat(result).isEqualTo(count);
	}
}
