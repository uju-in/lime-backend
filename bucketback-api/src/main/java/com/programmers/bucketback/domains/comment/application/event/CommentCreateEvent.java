package com.programmers.bucketback.domains.comment.application.event;

import java.util.Map;

import com.programmers.bucketback.domains.comment.domain.Comment;

public record CommentCreateEvent(
	Long commentWriterId,
	Long receiverId,
	Long feedId,
	String commentWriter
) {
	public static CommentCreateEvent from(
		final String nickname,
		final Comment comment
	) {
		return new CommentCreateEvent(
			comment.getMemberId(),
			comment.getFeed().getMemberId(),
			comment.getFeed().getId(),
			nickname
		);
	}

	public Map<String, Object> toMap(String alarmMessage) {
		return Map.ofEntries(
			Map.entry("alarmMessage", alarmMessage),
			Map.entry("memberId", this.commentWriterId()),
			Map.entry("receiverId", this.receiverId()),
			Map.entry("feedId", this.feedId()),
			Map.entry("commentWriter", this.commentWriter())
		);
	}
}
