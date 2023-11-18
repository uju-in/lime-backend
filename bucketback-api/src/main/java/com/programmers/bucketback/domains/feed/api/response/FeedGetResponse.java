package com.programmers.bucketback.domains.feed.api.response;

import java.util.List;

import com.programmers.bucketback.domains.feed.application.dto.response.FeedGetServiceResponse;
import com.programmers.bucketback.domains.feed.model.FeedInfo;
import com.programmers.bucketback.domains.feed.model.FeedItemInfo;
import com.programmers.bucketback.domains.member.model.MemberInfo;

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
