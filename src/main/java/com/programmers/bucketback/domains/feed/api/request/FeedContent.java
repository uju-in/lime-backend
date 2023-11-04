package com.programmers.bucketback.domains.feed.api.request;

import com.programmers.bucketback.domains.common.Hobby;

public record FeedContent(
	Hobby hobby,
	Long bucketId,
	String message
) {
}
