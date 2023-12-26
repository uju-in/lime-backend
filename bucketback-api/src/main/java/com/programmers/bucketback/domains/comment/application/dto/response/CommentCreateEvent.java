package com.programmers.bucketback.domains.comment.application.dto.response;

import java.util.Map;

import com.programmers.bucketback.domains.comment.domain.Comment;

public record CommentCreateEvent(
	Long commentId,
	Long memberId,
	Long receiverId,
	Long feedId,
	String commentWriter
) {
	public static CommentCreateEvent from(
		final String nickName,
		final Comment comment
	) {
		return new CommentCreateEvent(
			comment.getId(),
			comment.getMemberId(),
			comment.getFeed().getMemberId(),
			comment.getFeed().getId(),
			nickName
		);
	}

	public Map<String, Object> toMap(String alarmType) {
		return Map.ofEntries(
			Map.entry("alarmType", alarmType),
			Map.entry("commentId", this.commentId()),
			Map.entry("memberId", this.memberId()),
			Map.entry("receiverId", this.receiverId()),
			Map.entry("feedId", this.feedId()),
			Map.entry("nickName", this.commentWriter)
		);
	}
}
