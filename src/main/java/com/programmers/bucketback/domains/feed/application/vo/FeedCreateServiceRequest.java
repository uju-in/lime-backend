package com.programmers.bucketback.domains.feed.application.vo;

public record FeedCreateServiceRequest(
	Long bucketId,
	String content
) {
}
