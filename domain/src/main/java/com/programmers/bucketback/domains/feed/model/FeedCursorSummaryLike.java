package com.programmers.bucketback.domains.feed.model;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.bucketback.common.cursor.CursorIdParser;
import com.programmers.bucketback.domains.member.model.MemberInfo;

import lombok.Builder;

@Builder
public record FeedCursorSummaryLike(
	String cursorId,
	MemberInfo memberInfo,
	Long feedId,
	String content,
	int likeCount,
	int commentCount,
	LocalDateTime createdAt,
	List<FeedCursorItem> feedItems,
	boolean isLike
) implements CursorIdParser {
}
