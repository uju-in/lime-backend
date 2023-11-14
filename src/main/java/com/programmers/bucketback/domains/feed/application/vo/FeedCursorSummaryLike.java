package com.programmers.bucketback.domains.feed.application.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.bucketback.domains.member.application.vo.MemberInfo;

import lombok.Builder;

@Builder
public record FeedCursorSummaryLike(
	String cursorId,
	MemberInfo memberInfo,
	Long feedId,
	String feedContent,
	int likeCount,
	int commentCount,
	LocalDateTime createdAt,
	List<FeedCursorItem> feedItems,
	boolean isLike
) {
}
