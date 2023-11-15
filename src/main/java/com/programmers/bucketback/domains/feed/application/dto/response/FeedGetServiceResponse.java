package com.programmers.bucketback.domains.feed.application.dto.response;

import com.programmers.bucketback.domains.feed.application.vo.FeedInfo;
import com.programmers.bucketback.domains.feed.application.vo.FeedItemInfo;
import com.programmers.bucketback.domains.member.application.vo.MemberInfo;

import java.util.List;

public record FeedGetServiceResponse(
	MemberInfo memberInfo,
	FeedInfo feedInfo,
	List<FeedItemInfo> feedItems
) {
}
