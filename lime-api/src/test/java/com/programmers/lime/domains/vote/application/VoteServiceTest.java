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
import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.setup.ItemSetup;
import com.programmers.lime.domains.item.model.ItemInfo;
import com.programmers.lime.domains.vote.application.dto.request.VoteCreateServiceRequest;
import com.programmers.lime.domains.vote.application.dto.response.VoteGetServiceResponse;
import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.Voter;
import com.programmers.lime.domains.vote.domain.setup.VoteSetUp;
import com.programmers.lime.domains.vote.domain.setup.VoterSetUp;
import com.programmers.lime.domains.vote.implementation.VoteReader;
import com.programmers.lime.domains.vote.model.VoteDetailInfo;
import com.programmers.lime.domains.vote.model.VoteSortCondition;
import com.programmers.lime.domains.vote.model.VoteStatusCondition;
import com.programmers.lime.domains.vote.model.VoteSummary;
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
	private VoteReader voteReader;

	@Autowired
	private ItemSetup itemSetup;

	@MockBean
	private MemberUtils memberUtils;

	@MockBean
	private VoteRedisManager voteRedisManager;

	private Item item1;
	private Item item2;
	private Vote vote;
	private Long voteId;

	@BeforeEach
	void setUp() {
		item1 = itemSetup.saveOne(1L);
		item2 = itemSetup.saveOne(2L);
		vote = voteSetup.saveOne(1L, item1.getId(), item2.getId());
		voteId = vote.getId();
	}

	@Nested
	class CreateVote {
		@Test
		@DisplayName("회원은 투표를 생성할 수 있다.")
		void createVoteTest() {
			// given
			final Long item1Id = item1.getId();
			final Long item2Id = item2.getId();
			final VoteCreateServiceRequest request = VoteCreateServiceRequest.builder()
				.hobby(Hobby.BASKETBALL)
				.content("농구공 추천 좀 해주세요!")
				.item1Id(item1Id)
				.item2Id(item2Id)
				.maximumParticipants(1000)
				.build();

			given(memberUtils.getCurrentMemberId())
				.willReturn(1L);

			willDoNothing()
				.given(voteRedisManager)
				.addRanking(anyString(), any(VoteRankingInfo.class));

			// when
			final Long result = voteService.createVote(request);

			// then
			assertThat(result).isNotNull();

			// verify
			then(voteRedisManager).should(times(1))
				.addRanking(
					String.valueOf(Hobby.BASKETBALL),
					VoteRankingInfo.builder()
						.id(Long.MAX_VALUE - result)
						.item1Image(item1.getImage())
						.item2Image(item2.getImage())
						.build()
				);
		}

		@Test
		@DisplayName("동일한 투표 아이템으로 투표를 생성할 수 없다.")
		void createVoteWithSameItemTest() {
			// given
			final Long sameItemId = item1.getId();
			final VoteCreateServiceRequest request = VoteCreateServiceRequest.builder()
				.hobby(Hobby.BASKETBALL)
				.content("농구공 추천 좀 해주세요!")
				.item1Id(sameItemId)
				.item2Id(sameItemId)
				.maximumParticipants(1000)
				.build();

			given(memberUtils.getCurrentMemberId())
				.willReturn(1L);

			// when & then
			assertThatThrownBy(() -> voteService.createVote(request))
				.isInstanceOf(BusinessException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.VOTE_ITEM_DUPLICATED);

			// verify
			then(voteRedisManager).shouldHaveNoInteractions();
		}

		@Test
		@DisplayName("존재하지 않는 아이템으로 투표를 생성할 수 없다.")
		void createVoteWithNotExistItemTest() {
			// given
			final Long notExistItem1Id = 3L;
			final Long notExistItem2Id = 4L;
			final VoteCreateServiceRequest request = VoteCreateServiceRequest.builder()
				.hobby(Hobby.BASKETBALL)
				.content("농구공 추천 좀 해주세요!")
				.item1Id(notExistItem1Id)
				.item2Id(notExistItem2Id)
				.maximumParticipants(1000)
				.build();

			given(memberUtils.getCurrentMemberId())
				.willReturn(1L);

			// when & then
			assertThatThrownBy(() -> voteService.createVote(request))
				.isInstanceOf(EntityNotFoundException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.ITEM_NOT_FOUND);

			// verify
			then(voteRedisManager).shouldHaveNoInteractions();
		}
	}

	@Transactional // 지연 로딩을 위해 필요
	@Nested
	class ParticipateVote {
		@Test
		@DisplayName("회원은 투표 아이템 중 하나를 선택하여 투표할 수 있다.")
		void participateVoteTest() {
			// given
			final Long itemId = vote.getItem1Id();

			given(memberUtils.getCurrentMemberId())
				.willReturn(1L);

			willDoNothing()
				.given(voteRedisManager)
				.updateRanking(anyString(), eq(true), any(VoteRankingInfo.class));

			// when
			voteService.participateVote(voteId, itemId);

			// then
			assertThat(vote.getVoters()).hasSize(1);

			// verify
			then(voteRedisManager).should(times(1))
				.updateRanking(
					String.valueOf(vote.getHobby()),
					true,
					VoteRankingInfo.builder()
						.id(Long.MAX_VALUE - voteId)
						.item1Image(item1.getImage())
						.item2Image(item2.getImage())
						.build()
				);
		}

		@Test
		@DisplayName("회원은 이미 참여한 투표에 다시 투표할 수 있다.")
		void reParticipateVoteTest() {
			// given
			final Long memberId = 1L;
			final Long selectedItemId = vote.getItem1Id();
			final Voter voter = voterSetup.saveOne(vote, memberId, selectedItemId);
			final Long reSelectedItemId = vote.getItem2Id();

			given(memberUtils.getCurrentMemberId())
				.willReturn(memberId);

			// when
			voteService.participateVote(voteId, reSelectedItemId);

			// then
			assertThat(vote.getVoters()).hasSize(1);
			assertThat(voter.getItemId()).isEqualTo(reSelectedItemId);

			// verify
			then(voteRedisManager).shouldHaveNoInteractions();
		}

		@Test
		@DisplayName("종료된 투표에 참여할 수 없다.")
		void participateVoteWithClosedVoteTest() {
			// given
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
		@DisplayName("투표에 없는 아이템으로 참여할 수 없다.")
		void participateVoteWithNotExistItemTest() {
			// given
			final Long notExistItemId = 3L;

			given(memberUtils.getCurrentMemberId())
				.willReturn(1L);

			// when & then
			assertThatThrownBy(() -> voteService.participateVote(voteId, notExistItemId))
				.isInstanceOf(BusinessException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.VOTE_NOT_CONTAIN_ITEM);

			// verify
			then(voteRedisManager).shouldHaveNoInteractions();
		}
	}

	@Test
	@DisplayName("회원은 투표 참여를 취소할 수 있다.")
	void cancelVoteTest() {
		// given
		final Long memberId = 1L;
		voterSetup.saveOne(vote, memberId, vote.getItem1Id());

		given(memberUtils.getCurrentMemberId())
			.willReturn(memberId);

		willDoNothing()
			.given(voteRedisManager)
			.decreasePopularity(anyString(), any(VoteRankingInfo.class));

		// when
		voteService.cancelVote(voteId);

		// then
		assertThat(vote.getVoters()).isEmpty();

		// verify
		then(voteRedisManager).should(times(1))
			.decreasePopularity(
				String.valueOf(vote.getHobby()),
				VoteRankingInfo.builder()
					.id(Long.MAX_VALUE - voteId)
					.item1Image(item1.getImage())
					.item2Image(item2.getImage())
					.build()
			);
	}

	@Nested
	class DeleteVote {
		@Test
		@DisplayName("투표 생성자는 투표를 삭제할 수 있다.")
		void deleteVoteTest() {
			// given
			final Long memberId = vote.getMemberId();

			given(memberUtils.getCurrentMemberId())
				.willReturn(memberId);

			willDoNothing()
				.given(voteRedisManager)
				.remove(anyString(), any(VoteRankingInfo.class));

			// when
			voteService.deleteVote(voteId);

			// then
			assertThatThrownBy(() -> voteReader.read(voteId)) // 삭제된 투표 조회 시 EntityNotFoundException 발생
				.isInstanceOf(EntityNotFoundException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.VOTE_NOT_FOUND);

			// verify
			then(voteRedisManager).should(times(1))
				.remove(
					String.valueOf(vote.getHobby()),
					VoteRankingInfo.builder()
						.id(Long.MAX_VALUE - voteId)
						.item1Image(item1.getImage())
						.item2Image(item2.getImage())
						.build()
				);
		}

		@Test
		@DisplayName("투표 생성자가 아닌 경우 투표를 삭제할 수 없다.")
		void deleteVoteWithNotOwnerTest() {
			// given
			final Long notOwnerMemberId = 2L;

			given(memberUtils.getCurrentMemberId())
				.willReturn(notOwnerMemberId);

			// when & then
			assertThatThrownBy(() -> voteService.deleteVote(voteId))
				.isInstanceOf(BusinessException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.VOTE_NOT_OWNER);

			// verify
			then(voteRedisManager).shouldHaveNoInteractions();
		}
	}

	@Nested
	class RaadVote {
		@Test
		@DisplayName("사용자는 투표를 상세 조회할 수 있다.") // 사용자는 회원과 비회원을 모두 의미
		void readVoteTest() {
			// when
			final VoteGetServiceResponse result = voteService.getVote(voteId);

			// then
			assertThat(result.item1Info()).isEqualTo(ItemInfo.from(item1));
			assertThat(result.item2Info()).isEqualTo(ItemInfo.from(item2));
			assertThat(result.voteInfo()).isEqualTo(VoteDetailInfo.of(vote, 0, 0));
			assertThat(result.isOwner()).isFalse();
			assertThat(result.selectedItemId()).isNull();
		}

		@Test
		@DisplayName("회원이 생성한 투표를 상세 조회 시 본인이 생성한 투표임을 확인할 수 있다.")
		void readVoteWithOwnerTest() {
			// given
			final Long memberId = vote.getMemberId();

			given(memberUtils.getCurrentMemberId())
				.willReturn(memberId);

			// when
			final VoteGetServiceResponse result = voteService.getVote(voteId);

			// then
			assertThat(result.isOwner()).isTrue();
		}

		@Test
		@DisplayName("회원이 참여한 투표를 상세 조회 시 본인이 선택한 아이템을 확인할 수 있다.")
		void readVoteWithParticipatedTest() {
			// given
			final Long memberId = 1L;
			final Long selectedItemId = vote.getItem1Id();
			voterSetup.saveOne(vote, memberId, selectedItemId);

			given(memberUtils.getCurrentMemberId())
				.willReturn(memberId);

			// when
			final VoteGetServiceResponse result = voteService.getVote(voteId);

			// then
			assertThat(result.selectedItemId()).isEqualTo(selectedItemId);
		}
	}

	@Nested
	class GetVotesByCursor {

		Vote vote2;
		Vote vote3;

		@BeforeEach
		void setUp() {
			vote2 = voteSetup.saveOne(2L, item1.getId(), item2.getId());
			vote3 = voteSetup.saveOne(3L, item1.getId(), item2.getId());
		}

		@Test
		@DisplayName("사용자는 투표 목록을 취미별로 최신순으로 조회할 수 있다.")
		void getVotesByCursorWithRecentTest() {
			// given
			final Hobby hobby = Hobby.BASKETBALL;
			final VoteSortCondition sortCondition = VoteSortCondition.RECENT;

			// when
			final CursorSummary<VoteSummary> result = voteService.getVotesByCursor(
				hobby,
				null,
				sortCondition,
				new CursorPageParameters(null, null)
			);

			// then
			assertThat(result.summaries()).hasSize(3);
			assertThat(result.summaries().get(0).voteInfo().id()).isEqualTo(vote3.getId());
			assertThat(result.summaries().get(1).voteInfo().id()).isEqualTo(vote2.getId());
			assertThat(result.summaries().get(2).voteInfo().id()).isEqualTo(vote.getId());
		}

		@Test
		@DisplayName("사용자는 투표 목록을 취미별로 인기순으로 조회할 수 있다.")
		void getVotesByCursorWithPopularTest() {
			// given
			final Hobby hobby = Hobby.BASKETBALL;
			final VoteSortCondition sortCondition = VoteSortCondition.POPULARITY;

			voterSetup.saveOne(vote2, 1L, vote.getItem1Id());
			voterSetup.saveOne(vote2, 2L, vote.getItem1Id());
			voterSetup.saveOne(vote3, 1L, vote.getItem1Id());

			// when
			final CursorSummary<VoteSummary> result = voteService.getVotesByCursor(
				hobby,
				null,
				sortCondition,
				new CursorPageParameters(null, null)
			);

			// then
			assertThat(result.summaries()).hasSize(3);
			assertThat(result.summaries().get(0).voteInfo().id()).isEqualTo(vote2.getId());
			assertThat(result.summaries().get(1).voteInfo().id()).isEqualTo(vote3.getId());
			assertThat(result.summaries().get(2).voteInfo().id()).isEqualTo(vote.getId());
		}

		@Test
		@DisplayName("사용자는 투표 목록을 취미별로 투표 마감순으로 조회할 수 있다.")
		void getVotesByCursorWithClosedTest() {
			// given
			final Hobby hobby = Hobby.BASKETBALL;
			final VoteSortCondition sortCondition = VoteSortCondition.CLOSED;

			// when
			final CursorSummary<VoteSummary> result = voteService.getVotesByCursor(
				hobby,
				null,
				sortCondition,
				new CursorPageParameters(null, null)
			);

			// then
			assertThat(result.summaries()).hasSize(3);
			assertThat(result.summaries().get(0).voteInfo().id()).isEqualTo(vote.getId());
			assertThat(result.summaries().get(1).voteInfo().id()).isEqualTo(vote2.getId());
			assertThat(result.summaries().get(2).voteInfo().id()).isEqualTo(vote3.getId());
		}

		@Test
		@DisplayName("회원은 본인이 생성한 투표 목록을 취미별로 조회할 수 있다.")
		void getVotesByCursorWithPostedTest() {
			// given
			final Hobby hobby = Hobby.BASKETBALL;
			final VoteStatusCondition statusCondition = VoteStatusCondition.POSTED;
			final Long memberId = 1L;

			given(memberUtils.getCurrentMemberId())
				.willReturn(memberId);

			// when
			final CursorSummary<VoteSummary> result = voteService.getVotesByCursor(
				hobby,
				statusCondition,
				VoteSortCondition.RECENT,
				new CursorPageParameters(null, null)
			);

			// then
			assertThat(result.summaries()).hasSize(3);
		}

		@Test
		@DisplayName("회원은 본인이 참여한 투표 목록을 취미별로 조회할 수 있다.")
		void getVotesByCursorWithParticipatedTest() {
			// given
			final Hobby hobby = Hobby.BASKETBALL;
			final VoteStatusCondition statusCondition = VoteStatusCondition.PARTICIPATED;
			final Long memberId = 1L;

			voterSetup.saveOne(vote2, memberId, vote.getItem1Id());
			voterSetup.saveOne(vote3, memberId, vote.getItem1Id());

			given(memberUtils.getCurrentMemberId())
				.willReturn(memberId);

			// when
			final CursorSummary<VoteSummary> result = voteService.getVotesByCursor(
				hobby,
				statusCondition,
				VoteSortCondition.RECENT,
				new CursorPageParameters(null, null)
			);

			// then
			assertThat(result.summaries()).hasSize(2);
			assertThat(result.summaries().get(0).voteInfo().id()).isEqualTo(vote3.getId());
			assertThat(result.summaries().get(1).voteInfo().id()).isEqualTo(vote2.getId());
		}

		@Test
		@DisplayName("비회원은 본인이 생성하거나 참여한 투표 목록을 조회할 수 없다.")
		void getVotesByCursorWithUnauthorizedTest() {
			// given
			final Hobby hobby = Hobby.BASKETBALL;
			final VoteStatusCondition statusCondition = VoteStatusCondition.POSTED;
			final Long memberId = null;

			given(memberUtils.getCurrentMemberId())
				.willReturn(memberId);

			// when & then
			assertThatThrownBy(() -> voteService.getVotesByCursor(
				hobby,
				statusCondition,
				VoteSortCondition.RECENT,
				new CursorPageParameters(null, null)
			))
				.isInstanceOf(BusinessException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.UNAUTHORIZED);
		}
	}
}
