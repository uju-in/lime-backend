package com.programmers.lime.domains.feed.application.dto.response;

import java.util.List;

import com.programmers.lime.domains.feed.model.FeedDetail;
import com.programmers.lime.domains.feed.model.FeedInfo;
import com.programmers.lime.domains.feed.model.FeedItemInfo;
import com.programmers.lime.domains.member.model.MemberInfo;

public record FeedGetServiceResponse(
	MemberInfo memberInfo,
	FeedInfo feedInfo,
	List<FeedItemInfo> feedItems
) {
	public static FeedGetServiceResponse from(final FeedDetail detail) {
		return new FeedGetServiceResponse(detail.memberInfo(), detail.feedInfo(), detail.feedItems());
	}
}
