package com.programmers.lime.domains.vote.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.programmers.lime.IntegrationTest;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.setup.ItemSetup;
import com.programmers.lime.domains.vote.application.dto.request.VoteCreateServiceRequest;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.util.MemberUtils;
import com.programmers.lime.redis.vote.VoteRedisManager;

class VoteServiceTest extends IntegrationTest {

	@Autowired
	private VoteService voteService;

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
}
