package com.programmers.bucketback.domains.feed.api.request;

import com.programmers.bucketback.domains.feed.application.vo.FeedUpdateServiceRequest;

public record FeedUpdateRequest(String content) {

	public FeedUpdateServiceRequest toServiceRequest() {
		return new FeedUpdateServiceRequest(content);
	}
}
