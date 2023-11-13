package com.programmers.bucketback.domains.feed.application.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.feed.application.vo.FeedInfo;
import com.programmers.bucketback.domains.feed.application.vo.FeedItemInfo;
import com.programmers.bucketback.domains.member.application.vo.MemberInfo;

public record GetFeedServiceResponse(
	MemberInfo memberInfo,
	FeedInfo feedInfo,
	List<FeedItemInfo> feedItems
) {
}
