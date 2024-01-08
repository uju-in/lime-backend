package com.programmers.lime.domains.feed.application.dto.request;

import com.programmers.lime.common.model.Hobby;

import lombok.Builder;

@Builder
public record FeedGetCursorServiceRequest(
	Long myPageMemberId,
	Long loginMemberId,
	Hobby hobby
) {
}