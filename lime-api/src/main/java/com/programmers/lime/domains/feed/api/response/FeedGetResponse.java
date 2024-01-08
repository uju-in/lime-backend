package com.programmers.lime.domains.feed.api.response;

import java.util.List;

import com.programmers.lime.domains.feed.application.dto.response.FeedGetServiceResponse;
import com.programmers.lime.domains.feed.model.FeedInfo;
import com.programmers.lime.domains.feed.model.FeedItemInfo;
import com.programmers.lime.domains.member.model.MemberInfo;

public record FeedGetResponse(
	MemberInfo memberInfo,
	FeedInfo feedInfo,
	List<FeedItemInfo> feedItems
) {
	public static FeedGetResponse from(final FeedGetServiceResponse serviceResponse) {
		return new FeedGetResponse(
			serviceResponse.memberInfo(), serviceResponse.feedInfo(), serviceResponse.feedItems()
		);
	}
}
