package com.programmers.lime.domains.friendships.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.friendships.domain.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
	Optional<Friendship> findByToMemberIdAndFromMemberId(
		final Long toMemberId,
		final Long fromMemberId
	);
}
