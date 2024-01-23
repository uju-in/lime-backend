package com.programmers.lime.domains.friendships.implementation;

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
import com.programmers.lime.domains.friendship.domain.FriendshipBuilder;
import com.programmers.lime.domains.friendship.model.FriendshipSummaryBuilder;
import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.friendships.model.FriendshipSummary;
import com.programmers.lime.domains.friendships.repository.FriendshipRepository;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.MemberBuilder;
import com.programmers.lime.error.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class FriendshipReaderTest {

	@InjectMocks
	private FriendshipReader friendshipReader;

	@Mock
	private FriendshipRepository friendshipRepository;

	@Test
	@DisplayName("팔로워를 조회한다.")
	void readFollowerByCursorTest() {
		// given
		final Member member = MemberBuilder.build(1L);
		final String nickname = member.getNickname();
		final CursorPageParameters parameters = CursorPageParametersBuilder.build();

		final int pageSize = parameters.size() == null ? 20 : parameters.size();
		final List<Friendship> friendships = FriendshipBuilder.buildFollower(member, pageSize);
		final List<FriendshipSummary> friendshipSummaries = FriendshipSummaryBuilder.buildFollower(friendships);
		final CursorSummary<FriendshipSummary> cursorSummaries = CursorUtils.getCursorSummaries(friendshipSummaries);

		given(friendshipRepository.findFollowerByCursor(
			nickname,
			parameters.cursorId(),
			pageSize
		)).willReturn(friendshipSummaries);

		// when
		CursorSummary<FriendshipSummary> result = friendshipReader.readFollowerByCursor(nickname, parameters);

		// then
		assertThat(result).usingRecursiveComparison()
			.isEqualTo(cursorSummaries);
	}

	@Test
	@DisplayName("팔로잉을 조회한다.")
	void readFollowingByCursorTest() {
		// given
		final Member member = MemberBuilder.build(1L);
		final String nickname = member.getNickname();
		final CursorPageParameters parameters = CursorPageParametersBuilder.build();

		final int pageSize = parameters.size() == null ? 20 : parameters.size();
		final List<Friendship> friendships = FriendshipBuilder.buildFollowing(member, pageSize);
		final List<FriendshipSummary> friendshipSummaries = FriendshipSummaryBuilder.buildFollowing(friendships);
		final CursorSummary<FriendshipSummary> cursorSummaries = CursorUtils.getCursorSummaries(friendshipSummaries);

		given(friendshipRepository.findFollowingByCursor(
			nickname,
			parameters.cursorId(),
			pageSize
		)).willReturn(friendshipSummaries);

		// when
		CursorSummary<FriendshipSummary> result = friendshipReader.readFollowingByCursor(nickname, parameters);

		// then
		assertThat(result).usingRecursiveComparison()
			.isEqualTo(cursorSummaries);
	}

	@Nested
	@DisplayName("친구 관계 조회 테스트")
	class ReadTest {
		@Test
		@DisplayName("친구 관계를 조회한다.")
		void readTest() {
			// given
			final Member toMember = MemberBuilder.build(1L);
			final Member fromMember = MemberBuilder.build(2L);
			final Friendship friendship = FriendshipBuilder.build(toMember, fromMember);

			given(friendshipRepository.findByToMemberAndFromMember(toMember, fromMember))
				.willReturn(Optional.of(friendship));

			// when
			final Friendship result = friendshipReader.read(toMember, fromMember);

			// then
			assertThat(result).usingRecursiveComparison()
				.isEqualTo(friendship);
		}

		@Test
		@DisplayName("친구 관계를 조회하지 못한 경우 예외를 던진다.")
		void readTestNotFound() {
			// given
			final Member toMember = MemberBuilder.build(1L);
			final Member fromMember = MemberBuilder.build(2L);

			given(friendshipRepository.findByToMemberAndFromMember(toMember, fromMember))
				.willReturn(Optional.empty());

			// when & then
			assertThatThrownBy(() -> friendshipReader.read(toMember, fromMember))
				.isInstanceOf(EntityNotFoundException.class);
		}
	}
}
