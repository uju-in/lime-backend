package com.programmers.lime.domains.friendships.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.domains.friendships.repository.FriendshipRepository;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.MemberBuilder;

@ExtendWith(MockitoExtension.class)
class FriendshipCounterTest {

	@InjectMocks
	private FriendshipCounter friendshipCounter;

	@Mock
	private FriendshipRepository friendshipRepository;

	@Test
	@DisplayName("팔로워 수를 조회한다.")
	void countFollowerTest() {
		// given
		final Member member = MemberBuilder.build(1L);
		final Long followerCount = 10L;

		given(friendshipRepository.countByToMember(member))
			.willReturn(followerCount);

		// when
		final Long result = friendshipCounter.countFollower(member);

		// then
		assertThat(result).isEqualTo(followerCount);
	}

	@Test
	@DisplayName("팔로잉 수를 조회한다.")
	void countFollowingTest() {
		// given
		final Member member = MemberBuilder.build(1L);
		final Long followingCount = 10L;

		given(friendshipRepository.countByFromMember(member))
			.willReturn(followingCount);

		// when
		final Long result = friendshipCounter.countFollowing(member);

		// then
		assertThat(result).isEqualTo(followingCount);
	}
}
