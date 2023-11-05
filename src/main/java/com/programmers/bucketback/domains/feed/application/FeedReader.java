package com.programmers.bucketback.domains.feed.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.repository.FeedRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedReader {

	private final FeedRepository feedRepository;

	public Feed read(
		final Long feedId,
		final Long memberId
	) {
		return feedRepository.findByIdAndMemberId(feedId, memberId)
			.orElseThrow(() -> {
				throw new EntityNotFoundException(ErrorCode.FEED_NOT_FOUND);
			});
	}
}
