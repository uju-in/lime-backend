package com.programmers.bucketback.domains.feed.model;

public record FeedCreateServiceRequest(
	Long bucketId,
	String content
) {
}
