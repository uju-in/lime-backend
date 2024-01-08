package com.programmers.lime.domains.feed.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedCreateServiceRequestBuilder {
	public static FeedCreateServiceRequest build(final Long bucketId) {
		return new FeedCreateServiceRequest(
			bucketId,
			"content"
		);
	}
}
