package com.programmers.bucketback.domains.feed.application.vo;

import com.programmers.bucketback.domains.common.Hobby;

public record FeedCreateContent(
	Hobby hobby,
	Long bucketId,
	String message
) {
}
