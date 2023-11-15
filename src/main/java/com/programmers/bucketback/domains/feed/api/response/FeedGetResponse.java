package com.programmers.bucketback.domains.feed.api.response;

import com.programmers.bucketback.domains.feed.application.dto.response.FeedGetServiceResponse;
import com.programmers.bucketback.domains.feed.application.vo.FeedInfo;
import com.programmers.bucketback.domains.feed.application.vo.FeedItemInfo;
import com.programmers.bucketback.domains.member.application.vo.MemberInfo;

import java.util.List;

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
