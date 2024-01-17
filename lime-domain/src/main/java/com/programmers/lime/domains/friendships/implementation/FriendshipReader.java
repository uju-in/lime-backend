package com.programmers.lime.domains.friendships.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.friendships.model.FriendshipSummary;
import com.programmers.lime.domains.friendships.repository.FriendshipRepository;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FriendshipReader {

	public static final int DEFAULT_PAGING_SIZE = 20;

	private final FriendshipRepository friendshipRepository;

	@Transactional(readOnly = true)
	public Friendship read(
		final Long toMemberId,
		final Long fromMemberId
	) {
		return friendshipRepository.findByToMemberIdAndFromMemberId(toMemberId, fromMemberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.FRIENDSHIP_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public CursorSummary<FriendshipSummary> readFollowerByCursor(
		final String nickname,
		final CursorPageParameters parameters
	) {
		final int pageSize = getPageSize(parameters);
		final List<FriendshipSummary> followerSummaries = friendshipRepository.findFollowerByCursor(
			nickname,
			parameters.cursorId(),
			pageSize
		);

		return CursorUtils.getCursorSummaries(followerSummaries);
	}

	@Transactional(readOnly = true)
	public CursorSummary<FriendshipSummary> readFollowingByCursor(
		final String nickname,
		final CursorPageParameters parameters
	) {
		final int pageSize = getPageSize(parameters);
		final List<FriendshipSummary> followerSummaries = friendshipRepository.findFollowingByCursor(
			nickname,
			parameters.cursorId(),
			pageSize
		);

		return CursorUtils.getCursorSummaries(followerSummaries);
	}

	private int getPageSize(final CursorPageParameters parameters) {
		return parameters.size() == null ? DEFAULT_PAGING_SIZE : parameters.size();
	}
}
