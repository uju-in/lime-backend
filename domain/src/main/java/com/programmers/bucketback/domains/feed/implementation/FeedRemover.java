package com.programmers.bucketback.domains.feed.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.repository.FeedLikeRepository;
import com.programmers.bucketback.domains.feed.repository.FeedRepository;
import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedRemover {

	private final FeedRepository feedRepository;
	private final FeedLikeRepository feedLikeRepository;
	private final FeedReader feedReader;

	@Transactional
	public void remove(
		final Long memberId,
		final Long feedId
	) {
		Feed feed = feedReader.read(feedId, memberId);

		feedRepository.delete(feed);
	}

	/** 피드 좋아요 취소 */
	@Transactional
	public void unlike(
		final Long memberId,
		final Long feedId
	) {
		Feed feed = feedReader.read(feedId);

		if (feedReader.alreadyLiked(memberId, feed)) {
			feedLikeRepository.deleteByMemberIdAndFeed(memberId, feed);
			return;
		}

		throw new BusinessException(ErrorCode.FEED_LIKE_NOT_FOUND);
	}
}
