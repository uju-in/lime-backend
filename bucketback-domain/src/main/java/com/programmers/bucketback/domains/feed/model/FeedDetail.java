package com.programmers.bucketback.domains.feed.model;

import java.util.List;

import com.programmers.bucketback.domains.member.model.MemberInfo;

public record FeedDetail(
	MemberInfo memberInfo,
	FeedInfo feedInfo,
	List<FeedItemInfo> feedItems
) {
}
