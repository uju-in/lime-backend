package com.programmers.lime.domains.chat.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import com.programmers.lime.domains.BaseEntity;
import com.programmers.lime.domains.chat.model.ChatType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "chats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "message", nullable = false)
	private String message;

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "chat_room_id", nullable = false)
	private Long chatRoomId;

	@Enumerated(EnumType.STRING)
	@Column(name = "chat_type", nullable = false)
	private ChatType chatType;

	@Column(name = "send_at", nullable = false)
	private LocalDateTime sendAt;

	@Builder
	public Chat(
		final String message,
		final Long memberId,
		final Long chatRoomId,
		final ChatType chatType,
		final LocalDateTime sendAt
	) {
		this.message = Objects.requireNonNull(message);
		this.memberId = Objects.requireNonNull(memberId);
		this.chatRoomId = Objects.requireNonNull(chatRoomId);
		this.chatType = Objects.requireNonNull(chatType);
		this.sendAt = Objects.requireNonNull(sendAt);
	}
}
