package com.programmers.bucketback.domains.feed.api.response;

import java.util.List;

import com.programmers.bucketback.domains.feed.application.dto.response.GetFeedServiceResponse;
import com.programmers.bucketback.domains.feed.application.vo.FeedInfo;
import com.programmers.bucketback.domains.feed.application.vo.FeedItemInfo;
import com.programmers.bucketback.domains.member.application.vo.MemberInfo;

public record FeedGetResponse(
	MemberInfo memberInfo,
	FeedInfo feedInfo,
	List<FeedItemInfo> feedItems
) {
	public static FeedGetResponse from(final GetFeedServiceResponse serviceResponse) {
		return new FeedGetResponse(
			serviceResponse.memberInfo(), serviceResponse.feedInfo(), serviceResponse.feedItems()
		);
	}
}
