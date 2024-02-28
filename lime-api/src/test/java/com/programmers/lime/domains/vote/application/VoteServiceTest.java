package com.programmers.lime.domains.vote.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.IntegrationTest;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.setup.ItemSetup;
import com.programmers.lime.domains.vote.application.dto.request.VoteCreateServiceRequest;
import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.Voter;
import com.programmers.lime.domains.vote.domain.setup.VoteSetUp;
import com.programmers.lime.domains.vote.domain.setup.VoterSetUp;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.util.MemberUtils;
import com.programmers.lime.redis.vote.VoteRankingInfo;
import com.programmers.lime.redis.vote.VoteRedisManager;

class VoteServiceTest extends IntegrationTest {

	@Autowired
	private VoteService voteService;

	@Autowired
	private VoteSetUp voteSetup;

	@Autowired
	private VoterSetUp voterSetup;

	@Autowired
	private ItemSetup itemSetup;

	@MockBean
	private MemberUtils memberUtils;

	@MockBean
	private VoteRedisManager voteRedisManager;

	@BeforeEach
	void setUp() {
		itemSetup.saveOne(1L);
		itemSetup.saveOne(2L);
	}

	@Nested
	class CreateVote {
		@Test
		@DisplayName("투표를 생성한다.")
		void createVoteTest() {
			// given
			final VoteCreateServiceRequest request = VoteCreateServiceRequest.builder()
				.hobby(Hobby.BASKETBALL)
				.content("농구공 추천 좀 해주세요!")
				.item1Id(1L)
				.item2Id(2L)
				.maximumParticipants(1000)
				.build();

			given(memberUtils.getCurrentMemberId())
				.willReturn(1L);

			willDoNothing()
				.given(voteRedisManager).addRanking(any(), any());

			// when
			final Long result = voteService.createVote(request);

			// then
			assertThat(result).isNotNull();
		}

		@Test
		@DisplayName("투표 아이템이 똑같다면 예외를 발생시킨다.")
		void createVoteWithSameItemTest() {
			// given
			final VoteCreateServiceRequest request = VoteCreateServiceRequest.builder()
				.hobby(Hobby.BASKETBALL)
				.content("농구공 추천 좀 해주세요!")
				.item1Id(1L)
				.item2Id(1L)
				.maximumParticipants(1000)
				.build();

			given(memberUtils.getCurrentMemberId())
				.willReturn(1L);

			// when & then
			assertThatThrownBy(() -> voteService.createVote(request))
				.isInstanceOf(BusinessException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.VOTE_ITEM_DUPLICATED);

			then(voteRedisManager).shouldHaveNoInteractions();
		}

		@Test
		@DisplayName("투표 아이템이 존재하지 않는다면 예외를 발생시킨다.")
		void createVoteWithNotExistItemTest() {
			// given
			final VoteCreateServiceRequest request = VoteCreateServiceRequest.builder()
				.hobby(Hobby.BASKETBALL)
				.content("농구공 추천 좀 해주세요!")
				.item1Id(3L)
				.item2Id(4L)
				.maximumParticipants(1000)
				.build();

			given(memberUtils.getCurrentMemberId())
				.willReturn(1L);

			// when & then
			assertThatThrownBy(() -> voteService.createVote(request))
				.isInstanceOf(EntityNotFoundException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.ITEM_NOT_FOUND);

			then(voteRedisManager).shouldHaveNoInteractions();
		}
	}

	@Transactional // 지연 로딩을 위해 필요
	@Nested
	class ParticipateVote {
		@Test
		@DisplayName("투표에 참여한다.")
		void participateVoteTest() {
			// given
			final Long voteId = 1L;
			final Long itemId = 1L;
			final Vote vote = voteSetup.saveOne(voteId, 1L, 2L);

			given(memberUtils.getCurrentMemberId())
				.willReturn(1L);

			willDoNothing()
				.given(voteRedisManager)
				.updateRanking(eq(String.valueOf(vote.getHobby())), eq(true), any(VoteRankingInfo.class));

			// when
			voteService.participateVote(voteId, itemId);

			// then
			assertThat(vote.getVoters()).hasSize(1);

			// verify
			then(voteRedisManager).should(times(1))
				.updateRanking(eq(String.valueOf(vote.getHobby())), eq(true), any(VoteRankingInfo.class));
		}

		@Test
		@DisplayName("투표에 재참여한다.")
		void reParticipateVoteTest() {
			// given
			final Long voteId = 1L;
			final Long itemId = 2L;
			final Vote vote = voteSetup.saveOne(voteId, 1L, 2L);
			final Voter voter = voterSetup.saveOne(vote, 1L, 1L);

			given(memberUtils.getCurrentMemberId())
				.willReturn(1L);

			// when
			voteService.participateVote(voteId, itemId);

			// then
			assertThat(vote.getVoters()).hasSize(1);
			assertThat(voter.getItemId()).isEqualTo(itemId);

			// verify
			then(voteRedisManager).shouldHaveNoInteractions();
		}

		@Test
		@DisplayName("종료된 투표에 참여하려고 하면 예외를 발생시킨다.")
		void participateVoteWithEndedVoteTest() {
			// given
			final Long voteId = 1L;
			final Vote vote = voteSetup.saveOne(voteId, 1L, 2L);

			vote.close(LocalDateTime.now());

			given(memberUtils.getCurrentMemberId())
				.willReturn(1L);

			// when & then
			assertThatThrownBy(() -> voteService.participateVote(voteId, 1L))
				.isInstanceOf(BusinessException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.VOTE_CANNOT_PARTICIPATE);

			// verify
			then(voteRedisManager).shouldHaveNoInteractions();
		}

		@Test
		@DisplayName("투표에 없는 아이템에 참여하려고 하면 예외를 발생시킨다.")
		void participateVoteWithNotExistItemTest() {
			// given
			final Long voteId = 1L;
			final Long itemId = 3L;
			voteSetup.saveOne(voteId, 1L, 2L);

			given(memberUtils.getCurrentMemberId())
				.willReturn(1L);

			// when & then
			assertThatThrownBy(() -> voteService.participateVote(voteId, itemId))
				.isInstanceOf(BusinessException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.VOTE_NOT_CONTAIN_ITEM);

			// verify
			then(voteRedisManager).shouldHaveNoInteractions();
		}
	}
}
