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
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

@ExtendWith(MockitoExtension.class)
class FriendshipRemoverTest {

	@InjectMocks
	private FriendshipRemover friendshipRemover;

	@Mock
	private FriendshipRepository friendshipRepository;

	@Mock
	private FriendshipReader friendshipReader;

	@Nested
	@DisplayName("언팔로우 테스트")
	class RemoveTest {
		@Test
		@DisplayName("팔로우를 취소한다.")
		void removeTest() {
			// given
			final Member toMember = MemberBuilder.build(1L);
			final Member fromMember = MemberBuilder.build(2L);
			final Friendship friendship = FriendshipBuilder.build(toMember, fromMember);

			given(friendshipReader.read(toMember, fromMember))
				.willReturn(friendship);

			willDoNothing()
				.given(friendshipRepository).delete(any(Friendship.class));

			// when
			friendshipRemover.remove(toMember, fromMember);

			// then
			then(friendshipRepository).should().delete(friendship);
		}

		@Test
		@DisplayName("팔로우하지 않은 경우 예외를 던진다.")
		void removeTestNotFriend() {
			// given
			final Member toMember = MemberBuilder.build(1L);
			final Member fromMember = MemberBuilder.build(2L);

			given(friendshipReader.read(toMember, fromMember))
				.willThrow(new EntityNotFoundException(ErrorCode.FRIENDSHIP_NOT_FOUND));

			// when & then
			assertThatThrownBy(() -> friendshipRemover.remove(toMember, fromMember))
				.isInstanceOf(EntityNotFoundException.class);
			then(friendshipRepository).shouldHaveNoInteractions();
		}
	}
}
