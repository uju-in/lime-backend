package com.programmers.lime.domains.chat.application;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.chat.application.dto.response.ChatGetCursorServiceResponse;
import com.programmers.lime.domains.chat.implementation.ChatAppender;
import com.programmers.lime.domains.chat.implementation.ChatReader;
import com.programmers.lime.domains.chat.model.ChatInfoWithMember;
import com.programmers.lime.domains.chat.model.ChatSummary;
import com.programmers.lime.domains.chat.model.ChatType;
import com.programmers.lime.domains.chatroom.implementation.ChatRoomMemberReader;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.config.security.SecurityUtils;
import com.programmers.lime.redis.chat.ChatSessionRedisManager;
import com.programmers.lime.redis.chat.model.ChatSessionInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final SimpMessagingTemplate simpMessagingTemplate;

	private final MemberReader memberReader;

	private final ChatSessionRedisManager chatSessionRedisManager;

	private final ChatReader chatReader;

	private final ChatAppender chatAppender;

	private final ChatRoomMemberReader chatRoomMemberReader;

	public void sendMessage(final Long memberId, final String sessionId, final String message, final String timeSeq) {
		Member member = memberReader.read(memberId);

		ChatSessionInfo sessionInfo = chatSessionRedisManager.getSessionInfo(sessionId);
		Long chatRoomId = sessionInfo.roomId();

		if (!memberId.equals(sessionInfo.memberId())) {
			throw new BusinessException(ErrorCode.CHAT_NOT_PERMISSION);
		}

		ChatInfoWithMember chatInfoWithMember = ChatInfoWithMember.builder()
			.message(message)
			.chatRoomId(chatRoomId)
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.memberId(member.getId())
			.chatType(ChatType.CHAT)
			.sendAt(getCreatedAt(timeSeq))
			.build();

		chatAppender.appendChat(chatInfoWithMember.toChatInfo());

		simpMessagingTemplate.convertAndSend("/subscribe/rooms/" + chatRoomId, chatInfoWithMember);
	}

	public void joinChatRoom(final Long chatRoomId) {
		Long memberId = SecurityUtils.getCurrentMemberId();
		Member member = memberReader.read(memberId);

		String message = member.getNickname() + "님이 참여하셨습니다.";

		String timeSeq = String.valueOf(System.currentTimeMillis());
		ChatInfoWithMember chatInfoWithMember = ChatInfoWithMember.builder()
			.message(message)
			.chatRoomId(chatRoomId)
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.memberId(member.getId())
			.chatType(ChatType.JOIN)
			.sendAt(getCreatedAt(timeSeq))
			.build();

		chatAppender.appendChat(chatInfoWithMember.toChatInfo());

		simpMessagingTemplate.convertAndSend("/subscribe/rooms/join/" + chatRoomId, chatInfoWithMember);
	}

	public void sendExitMessageToChatRoom(final Long chatRoomId) {
		Long memberId = SecurityUtils.getCurrentMemberId();
		Member member = memberReader.read(memberId);

		String message = member.getNickname() + "님이 퇴장하셨습니다.";

		String timeSeq = String.valueOf(System.currentTimeMillis());

		ChatInfoWithMember chatInfoWithMember = ChatInfoWithMember.builder()
			.message(message)
			.chatRoomId(chatRoomId)
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.memberId(member.getId())
			.chatType(ChatType.EXIT)
			.sendAt(getCreatedAt(timeSeq))
			.build();

		chatAppender.appendChat(chatInfoWithMember.toChatInfo());

		simpMessagingTemplate.convertAndSend("/subscribe/rooms/exit/" + chatRoomId, chatInfoWithMember);
	}

	public ChatGetCursorServiceResponse getChatByCursor(
		final Long chatRoomId,
		final CursorPageParameters parameters
	) {

		Long memberId = SecurityUtils.getCurrentMemberId();

		if (!chatRoomMemberReader.existMemberByMemberIdAndRoomId(chatRoomId, memberId)) {
			throw new BusinessException(ErrorCode.CHATROOM_NOT_PERMISSION);
		}

		CursorSummary<ChatSummary> summaries = chatReader.readByCursor(chatRoomId, parameters);

		return new ChatGetCursorServiceResponse(summaries);
	}

	private LocalDateTime getCreatedAt(final String timeSeq) {
		long longTimeSeq = Long.parseLong(timeSeq);
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(longTimeSeq), ZoneId.systemDefault());
	}
}