package com.programmers.bucketback.domains.feed.application.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.feed.model.FeedInfo;
import com.programmers.bucketback.domains.feed.model.FeedItemInfo;
import com.programmers.bucketback.domains.member.model.MemberInfo;

public record FeedGetServiceResponse(
	MemberInfo memberInfo,
	FeedInfo feedInfo,
	List<FeedItemInfo> feedItems
) {
}
