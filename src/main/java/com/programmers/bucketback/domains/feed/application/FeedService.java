package com.programmers.bucketback.domains.feed.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.feed.api.request.FeedContent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedService {

	private final FeedReader feedReader;
	private final FeedAppender feedAppender;
	private final FeedModifier feedModifier;
	private final FeedRemover feedRemover;

	/** 피드 생성 */
	public void createFeed(final FeedContent content) {
		feedAppender.append(content);
	}
}
