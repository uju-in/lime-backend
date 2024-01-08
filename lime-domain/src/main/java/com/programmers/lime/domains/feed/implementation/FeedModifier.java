package com.programmers.lime.domains.feed.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.feed.model.FeedUpdateServiceRequest;
import com.programmers.lime.domains.feed.domain.Feed;

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
