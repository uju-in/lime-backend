package com.programmers.lime.domains.friendships.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.domains.friendship.FriendshipBuilder;
import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.friendships.repository.FriendshipRepository;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.MemberBuilder;
import com.programmers.lime.error.BusinessException;

@ExtendWith(MockitoExtension.class)
class FriendshipAppenderTest {

	@InjectMocks
	private FriendshipAppender friendshipAppender;

	@Mock
	private FriendshipRepository friendshipRepository;

	@Nested
	@DisplayName("팔로우 테스트")
	class AppendTest {
		@Test
		@DisplayName("팔로우한다.")
		void appendTest() {
			// given
			final Member toMember = MemberBuilder.build(1L);
			final Member fromMember = MemberBuilder.build(2L);
			final Friendship friendship = FriendshipBuilder.build(toMember, fromMember);

			given(friendshipRepository.existsByToMemberAndFromMember(toMember, fromMember))
				.willReturn(false);

			given(friendshipRepository.save(any(Friendship.class)))
				.willReturn(friendship);

			// when
			final Friendship result = friendshipAppender.append(toMember, fromMember);

			// then
			assertThat(result).usingRecursiveComparison()
				.isEqualTo(friendship);
		}

		@Test
		@DisplayName("이미 팔로우한 경우 예외를 던진다.")
		void appendTestAlreadyExists() {
			// given
			final Member toMember = MemberBuilder.build(1L);
			final Member fromMember = MemberBuilder.build(2L);

			given(friendshipRepository.existsByToMemberAndFromMember(toMember, fromMember))
				.willReturn(true);

			// when & then
			assertThatThrownBy(() -> friendshipAppender.append(toMember, fromMember))
				.isInstanceOf(BusinessException.class);
		}
	}
}
