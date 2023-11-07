package com.programmers.bucketback.domains.feed.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.repository.FeedLikeRepository;
import com.programmers.bucketback.domains.feed.repository.FeedRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedReader {

	private final FeedRepository feedRepository;
	private final FeedLikeRepository feedLikeRepository;

	public Feed read(
		final Long feedId
	) {
		return feedRepository.findById(feedId)
			.orElseThrow(() -> {
				throw new EntityNotFoundException(ErrorCode.FEED_NOT_FOUND);
			});
	}

	public Feed read(final Long feedId) {
		return feedRepository.findById(feedId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.FEED_NOT_FOUND));
	}

	public Feed read(
		final Long feedId,
		final Long memberId
	) {
		return feedRepository.findByIdAndMemberId(feedId, memberId)
			.orElseThrow(() -> {
				throw new EntityNotFoundException(ErrorCode.FEED_NOT_FOUND);
			});
	}

	public boolean alreadyLiked(
		final Long memberId,
		final Feed feed
	) {
		return feedLikeRepository.existsByMemberIdAndFeed(memberId, feed);
	}
}
