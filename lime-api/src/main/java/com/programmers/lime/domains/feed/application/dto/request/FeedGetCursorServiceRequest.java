package com.programmers.lime.domains.feed.application.dto.request;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.feed.model.FeedSortCondition;

import lombok.Builder;

@Builder
public record FeedGetCursorServiceRequest(
	String nickname,
	FeedSortCondition sortCondition,
	Hobby hobby
) {
}