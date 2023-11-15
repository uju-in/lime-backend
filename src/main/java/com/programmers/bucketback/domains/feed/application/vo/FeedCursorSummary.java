package com.programmers.bucketback.domains.feed.application.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.bucketback.domains.member.application.vo.MemberInfo;

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
			.likeCount(likeCount)
			.commentCount(commentCount)
			.createdAt(createdAt)
			.feedItems(feedItems)
			.isLike(isLike)
			.build();
	}
}
