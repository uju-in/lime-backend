package com.programmers.bucketback.domains.feed.application.dto.request;

import com.programmers.bucketback.common.model.Hobby;

import lombok.Builder;

@Builder
public record FeedGetCursorServiceRequest(
	Long myPageMemberId,
	Long loginMemberId,
	Hobby hobby
) {
}