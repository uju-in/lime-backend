package com.programmers.lime.domains.vote.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

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
import com.programmers.lime.domains.vote.application.dto.response.VoteGetByKeywordServiceResponse;
import com.programmers.lime.domains.vote.application.dto.response.VoteGetServiceResponse;
import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.Voter;
import com.programmers.lime.domains.vote.domain.setup.VoteSetUp;
import com.programmers.lime.domains.vote.domain.setup.VoterSetUp;
import com.programmers.lime.domains.vote.implementation.VoteReader;
import com.programmers.lime.domains.vote.implementation.VoterReader;
import com.programmers.lime.domains.vote.model.VoteDetailInfo;
import com.programmers.lime.domains.vote.model.VoteSortCondition;
import com.programmers.lime.domains.vote.model.VoteStatusCondition;
import com.programmers.lime.domains.vote.model.VoteSummary;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.util.MemberUtils;

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
	private VoterReader voterReader;

	@Autowired
	private ItemSetup itemSetup;

	@MockBean
	private MemberUtils memberUtils;

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

			// when
			final Long result = voteService.createVote(request);

			// then
			assertThat(result).isNotNull();
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
			final Long memberId = 1L;

			given(memberUtils.getCurrentMemberId())
				.willReturn(memberId);

			// when
			voteService.participateVote(voteId, itemId);

			// then
			final Voter voter = voterReader.read(voteId, memberId);
			assertThat(voter.getItemId()).isEqualTo(itemId);
		}

		@Test
		@DisplayName("회원은 이미 참여한 투표에 다시 투표할 수 있다.")
		void reParticipateVoteTest() {
			// given
			final Long memberId = 1L;
			final Long selectedItemId = vote.getItem1Id();
			voterSetup.saveOne(voteId, memberId, selectedItemId);
			final Long reSelectedItemId = vote.getItem2Id();

			given(memberUtils.getCurrentMemberId())
				.willReturn(memberId);

			// when
			voteService.participateVote(voteId, reSelectedItemId);

			// then
			final Voter voter = voterReader.read(voteId, memberId);
			assertThat(voter.getItemId()).isEqualTo(reSelectedItemId);
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
		}
	}

	@Test
	@DisplayName("회원은 투표 참여를 취소할 수 있다.")
	void cancelVoteTest() {
		// given
		final Long memberId = 1L;
		voterSetup.saveOne(voteId, memberId, vote.getItem1Id());

		given(memberUtils.getCurrentMemberId())
			.willReturn(memberId);

		// when
		voteService.cancelVote(voteId);

		// then
		final Optional<Voter> voter = voterReader.find(voteId, memberId);
		assertThat(voter.isEmpty()).isTrue();
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

			// when
			voteService.deleteVote(voteId);

			// then
			assertThatThrownBy(() -> voteReader.read(voteId)) // 삭제된 투표 조회 시 EntityNotFoundException 발생
				.isInstanceOf(EntityNotFoundException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.VOTE_NOT_FOUND);
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
			voterSetup.saveOne(voteId, memberId, selectedItemId);

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

		Long vote2Id = 2L;
		Long vote3Id = 3L;

		@BeforeEach
		void setUp() {
			voteSetup.saveOne(vote2Id, item1.getId(), item2.getId());
			voteSetup.saveOne(vote3Id, item1.getId(), item2.getId());
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
			assertThat(result.summaries().get(0).voteInfo().id()).isEqualTo(vote3Id);
			assertThat(result.summaries().get(1).voteInfo().id()).isEqualTo(vote2Id);
			assertThat(result.summaries().get(2).voteInfo().id()).isEqualTo(voteId);
		}

		@Test
		@DisplayName("사용자는 투표 목록을 취미별로 인기순으로 조회할 수 있다.")
		void getVotesByCursorWithPopularTest() {
			// given
			final Hobby hobby = Hobby.BASKETBALL;
			final VoteSortCondition sortCondition = VoteSortCondition.POPULARITY;

			voterSetup.saveOne(vote2Id, 1L, vote.getItem1Id());
			voterSetup.saveOne(vote2Id, 2L, vote.getItem1Id());
			voterSetup.saveOne(vote3Id, 1L, vote.getItem1Id());

			// when
			final CursorSummary<VoteSummary> result = voteService.getVotesByCursor(
				hobby,
				null,
				sortCondition,
				new CursorPageParameters(null, null)
			);

			// then
			assertThat(result.summaries()).hasSize(3);
			assertThat(result.summaries().get(0).voteInfo().id()).isEqualTo(vote2Id);
			assertThat(result.summaries().get(0).voteInfo().participants()).isEqualTo(2);
			assertThat(result.summaries().get(1).voteInfo().id()).isEqualTo(vote3Id);
			assertThat(result.summaries().get(1).voteInfo().participants()).isEqualTo(1);
			assertThat(result.summaries().get(2).voteInfo().id()).isEqualTo(voteId);
			assertThat(result.summaries().get(2).voteInfo().participants()).isEqualTo(0);
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
			assertThat(result.summaries().get(0).voteInfo().id()).isEqualTo(voteId);
			assertThat(result.summaries().get(1).voteInfo().id()).isEqualTo(vote2Id);
			assertThat(result.summaries().get(2).voteInfo().id()).isEqualTo(vote3Id);
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
			assertThat(result.summaries().get(0).voteInfo().id()).isEqualTo(vote3Id);
			assertThat(result.summaries().get(1).voteInfo().id()).isEqualTo(vote2Id);
			assertThat(result.summaries().get(2).voteInfo().id()).isEqualTo(voteId);
		}

		@Test
		@DisplayName("회원은 본인이 참여한 투표 목록을 취미별로 조회할 수 있다.")
		void getVotesByCursorWithParticipatedTest() {
			// given
			final Hobby hobby = Hobby.BASKETBALL;
			final VoteStatusCondition statusCondition = VoteStatusCondition.PARTICIPATED;
			final Long memberId = 1L;

			voterSetup.saveOne(vote2Id, memberId, vote.getItem1Id());
			voterSetup.saveOne(vote3Id, memberId, vote.getItem1Id());

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
			assertThat(result.summaries().get(0).voteInfo().id()).isEqualTo(vote3Id);
			assertThat(result.summaries().get(1).voteInfo().id()).isEqualTo(vote2Id);
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

	@Nested
	class GetVotesByKeyword {

		Vote vote2;

		@BeforeEach
		void setUp() {
			vote2 = voteSetup.save(Vote.builder()
				.memberId(1L)
				.item1Id(item1.getId())
				.item2Id(item2.getId())
				.hobby(Hobby.BASKETBALL)
				.content("농린이 추천템은?")
				.maximumParticipants(1000)
				.build());
		}

		@Test
		@DisplayName("사용자는 투표 아이템명에 키워드가 포함된 투표 목록을 조회할 수 있다.")
		void getVotesByItemNameTest() {
			// given
			final String keyword = item1.getName();

			// when
			final VoteGetByKeywordServiceResponse result = voteService.getVotesByKeyword(
				keyword,
				new CursorPageParameters(null, 1)
			);

			// then
			assertThat(result.voteSummary().summaryCount()).isEqualTo(1);
			assertThat(result.voteSummary().summaries().get(0).voteInfo().id()).isEqualTo(vote2.getId());
			assertThat(result.totalVoteCount()).isEqualTo(2);
		}

		@Test
		@DisplayName("사용자는 투표 제목에 키워드가 포함된 투표 목록을 조회할 수 있다.")
		void getVotesByTitleTest() {
			// given
			final String keyword = "농린이";

			// when
			final VoteGetByKeywordServiceResponse result = voteService.getVotesByKeyword(
				keyword,
				new CursorPageParameters(null, 1)
			);

			// then
			assertThat(result.voteSummary().summaryCount()).isEqualTo(1);
			assertThat(result.voteSummary().summaries().get(0).voteInfo().id()).isEqualTo(vote2.getId());
			assertThat(result.totalVoteCount()).isEqualTo(1);
		}
	}
}
