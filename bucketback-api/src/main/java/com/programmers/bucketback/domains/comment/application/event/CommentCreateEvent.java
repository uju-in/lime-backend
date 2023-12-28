package com.programmers.bucketback.domains.comment.application.event;

import java.util.Map;

import com.programmers.bucketback.domains.comment.domain.Comment;
import com.programmers.bucketback.domains.sse.PayLoadProvider;
import com.programmers.bucketback.domains.sse.SsePayload;

import lombok.Builder;

@Builder
public record CommentCreateEvent(
	Long commentWriterId,
	Long receiverId,
	Long feedId,
	String commentWriter
) implements PayLoadProvider {

	public static SsePayload toSsePayload(
		String nickname,
		Comment comment
	) {
		CommentCreateEvent commentCreateEvent = of(nickname, comment);

		return SsePayload.builder()
			.receiverId(commentCreateEvent.receiverId())
			.data(commentCreateEvent.getPayloadData())
			.build();
	}

	private static CommentCreateEvent of(
		final String nickname,
		final Comment comment
	) {
		CommentCreateEvent commentCreateEvent = CommentCreateEvent.builder()
			.commentWriterId(comment.getMemberId())
			.receiverId(comment.getFeed().getMemberId())
			.feedId(comment.getFeed().getId())
			.commentWriter(nickname)
			.build();
		return commentCreateEvent;
	}

	@Override
	public Map<String, Object> getPayloadData() {
		return Map.ofEntries(
			Map.entry("alarmMessage", CommentEventType.COMMENT_CREATE.getDescription()),
			Map.entry("memberId", this.commentWriterId()),
			Map.entry("receiverId", this.receiverId()),
			Map.entry("feedId", this.feedId()),
			Map.entry("commentWriter", this.commentWriter())
		);
	}
}
