package com.programmers.lime.domains.feed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.feed.domain.Feed;
import com.programmers.lime.domains.feed.domain.FeedLike;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {
	boolean existsByMemberIdAndFeed(
		final Long memberId,
		final Feed feed
	);

	void deleteByMemberIdAndFeed(
		final Long memberId,
		final Feed feed
	);
}
