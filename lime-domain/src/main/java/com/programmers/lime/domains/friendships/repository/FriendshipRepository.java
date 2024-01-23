package com.programmers.lime.domains.friendships.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.friendships.domain.Friendship;
import com.programmers.lime.domains.member.domain.Member;

public interface FriendshipRepository extends JpaRepository<Friendship, Long>, FriendshipRepositoryForCursor {
	Optional<Friendship> findByToMemberAndFromMember(
		final Member toMember,
		final Member fromMember
	);

	long countByToMember(final Member member);

	long countByFromMember(final Member member);

	boolean existsByToMemberAndFromMember(
		final Member toMember,
		final Member fromMember
	);
}
