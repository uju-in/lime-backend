package com.programmers.bucketback.domains.feed.api.request;

import com.programmers.bucketback.domains.feed.application.vo.FeedUpdateContent;

public record FeedUpdateRequest(String message) {

	public FeedUpdateContent toContent() {
		return new FeedUpdateContent(message);
	}
}
