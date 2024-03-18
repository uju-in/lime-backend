package com.programmers.lime.domains.chatroom.domain;

import java.util.Objects;

import com.programmers.lime.domains.BaseEntity;
import com.programmers.lime.domains.chatroom.model.ChatRoomStatus;
import com.programmers.lime.domains.chatroom.model.ChatRoomType;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

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
@Table(name = "chat_rooms")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {

	private static final int MIN_ROOM_MAX_MEMBER_COUNT = 2;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "room_name", nullable = false)
	private String roomName;

	@Enumerated(EnumType.STRING)
	@Column(name = "room_type", nullable = false)
	private ChatRoomType chatRoomType;

	@Enumerated(EnumType.STRING)
	@Column(name = "room_status", nullable = false)
	private ChatRoomStatus chatRoomStatus;

	@Column(name = "room_max_member_count", nullable = false)
	private int roomMaxMemberCount;

	@Builder
	public ChatRoom(
		final String roomName,
		final ChatRoomType chatRoomType,
		final ChatRoomStatus chatRoomStatus,
		final int roomMaxMemberCount
	) {
		validRoomMaxMemberCount(roomMaxMemberCount);
		this.roomName = Objects.requireNonNull(roomName);
		this.chatRoomType = Objects.requireNonNull(chatRoomType);
		this.chatRoomStatus = Objects.requireNonNull(chatRoomStatus);
		this.roomMaxMemberCount = roomMaxMemberCount;
	}

	private void validRoomMaxMemberCount(int roomMaxMemberCount) {
		if (roomMaxMemberCount < MIN_ROOM_MAX_MEMBER_COUNT) {
			throw new BusinessException(ErrorCode.CHATROOM_MAX_MEMBER_COUNT_ERROR);
		}
	}
}
