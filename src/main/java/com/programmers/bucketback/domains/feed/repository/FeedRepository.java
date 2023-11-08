package com.programmers.bucketback.domains.feed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.feed.domain.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long> {
	Optional<Feed> findByIdAndMemberId(
		final Long feedId,
		final Long memberId
	);

}
