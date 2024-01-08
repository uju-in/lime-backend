package com.programmers.lime.domains.feed.model;

public record FeedCreateServiceRequest(
	Long bucketId,
	String content
) {
}
