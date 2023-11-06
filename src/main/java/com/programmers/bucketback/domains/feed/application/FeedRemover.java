package com.programmers.bucketback.domains.feed.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.repository.FeedRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedRemover {

	private final FeedRepository feedRepository;
	private final FeedReader feedReader;

	@Transactional
	public void remove(final Long feedId) {
		Long memberId = MemberUtils.getCurrentMemberId();
		Feed feed = feedReader.read(feedId, memberId);

		feedRepository.delete(feed);
	}
}
