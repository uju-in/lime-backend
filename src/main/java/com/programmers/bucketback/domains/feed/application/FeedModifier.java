package com.programmers.bucketback.domains.feed.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.feed.application.vo.FeedUpdateServiceRequest;
import com.programmers.bucketback.domains.feed.domain.Feed;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedModifier {

	private final FeedReader feedReader;

	/** 피드 수정 */
	@Transactional
	public void modify(
		final Long memberId,
		final Long feedId,
		final FeedUpdateServiceRequest request
	) {
		Feed feed = feedReader.read(feedId, memberId);

		feed.modifyFeed(request.content());
	}
}
