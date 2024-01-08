package com.programmers.lime.domains.feed.api.request;

import com.programmers.lime.domains.feed.model.FeedUpdateServiceRequest;

public record FeedUpdateRequest(String content) {

	public FeedUpdateServiceRequest toServiceRequest() {
		return new FeedUpdateServiceRequest(content);
	}
}
