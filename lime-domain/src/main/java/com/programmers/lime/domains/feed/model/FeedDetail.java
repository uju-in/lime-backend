package com.programmers.lime.domains.feed.model;

import java.util.List;

import com.programmers.lime.domains.member.model.MemberInfo;

public record FeedDetail(
	MemberInfo memberInfo,
	FeedInfo feedInfo,
	List<FeedItemInfo> feedItems
) {
}
