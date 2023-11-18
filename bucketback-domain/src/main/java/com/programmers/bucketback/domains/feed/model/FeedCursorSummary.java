package com.programmers.bucketback.domains.feed.model;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.bucketback.domains.member.model.MemberInfo;

import lombok.Builder;

@Builder
public record FeedCursorSummary(
	String cursorId,
	MemberInfo memberInfo,
	Long feedId,
	String content,
	int likeCount,
	int commentCount,
	LocalDateTime createdAt,
	List<FeedCursorItem> feedItems
) {
	public FeedCursorSummaryLike of(final boolean isLike) {
		return FeedCursorSummaryLike.builder()
			.cursorId(cursorId)
			.memberInfo(memberInfo)
			.feedId(feedId)
			.content(content)
			.likeCount(likeCount)
			.commentCount(commentCount)
			.createdAt(createdAt)
			.feedItems(feedItems)
			.isLike(isLike)
			.build();
	}
}
