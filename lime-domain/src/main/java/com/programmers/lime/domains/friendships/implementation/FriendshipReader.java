package com.programmers.lime.domains.friendships.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.friendships.repository.FriendshipRepository;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FriendshipReader {

	private final FriendshipRepository friendshipRepository;

	@Transactional(readOnly = true)
	public Friendship read(
		final Long toMemberId,
		final Long fromMemberId
	) {
		return friendshipRepository.findByToMemberIdAndFromMemberId(toMemberId, fromMemberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.FRIENDSHIP_NOT_FOUND));
	}
}
