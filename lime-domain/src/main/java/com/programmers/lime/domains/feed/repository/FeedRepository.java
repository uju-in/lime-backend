package com.programmers.lime.domains.feed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.feed.domain.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryForCursor {
	Optional<Feed> findByIdAndMemberId(
		final Long feedId,
		final Long memberId
	);

}
