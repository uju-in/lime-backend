package com.programmers.lime.domains.chat.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.programmers.lime.domains.chat.api.dto.response.ChatCreateResponse;
import com.programmers.lime.domains.chat.application.dto.response.ChatGetServiceResponse;
import com.programmers.lime.domains.chat.implementation.ChatAppender;
import com.programmers.lime.domains.chat.implementation.ChatReader;
import com.programmers.lime.domains.chat.model.ChatInfoWithMember;
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

	public void sendMessage(final Long memberId, final String sessionId, final String message) {
		Member member = memberReader.read(memberId);

		ChatSessionInfo sessionInfo = chatSessionRedisManager.getSessionInfo(sessionId);
		Long chatRoomId = sessionInfo.roomId();

		if(!memberId.equals(sessionInfo.memberId())) {
			throw new BusinessException(ErrorCode.CHAT_NOT_PERMISSION);
		}

		ChatCreateResponse chatCreateResponse = ChatCreateResponse.builder()
			.message(message)
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.memberId(memberId)
			.createdAt(LocalDateTime.now())
			.chatType(ChatType.CHAT)
			.build();

		chatAppender.appendChat(message, memberId, chatRoomId, ChatType.CHAT);

		simpMessagingTemplate.convertAndSend("/subscribe/rooms/" + chatRoomId, chatCreateResponse);
	}

	public void joinChatRoom(final Long chatRoomId) {
		Long memberId = SecurityUtils.getCurrentMemberId();
		Member member = memberReader.read(memberId);

		String message = member.getNickname() + "님이 참여하셨습니다.";

		ChatCreateResponse joinResponse = ChatCreateResponse.builder()
			.message(message)
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.memberId(memberId)
			.createdAt(LocalDateTime.now())
			.chatType(ChatType.JOIN)
			.build();

		chatAppender.appendChat(message, memberId, chatRoomId, ChatType.JOIN);

		simpMessagingTemplate.convertAndSend("/subscribe/rooms/join/" + chatRoomId, joinResponse);
	}

	public void sendExitMessageToChatRoom(final Long chatRoomId) {
		Long memberId = SecurityUtils.getCurrentMemberId();
		Member member = memberReader.read(memberId);

		String message = member.getNickname() + "님이 퇴장하셨습니다.";

		ChatCreateResponse exitResponse = ChatCreateResponse.builder()
			.message(message)
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.memberId(memberId)
			.createdAt(LocalDateTime.now())
			.chatType(ChatType.EXIT)
			.build();

		chatAppender.appendChat(message, memberId, chatRoomId, ChatType.EXIT);

		simpMessagingTemplate.convertAndSend("/subscribe/rooms/exit/" + chatRoomId, exitResponse);
	}

	public ChatGetServiceResponse getChatWithMemberList(final Long chatRoomId) {

		Long memberId = SecurityUtils.getCurrentMemberId();

		if(!chatRoomMemberReader.existMemberByMemberIdAndRoomId(chatRoomId, memberId)) {
			throw new BusinessException(ErrorCode.CHATROOM_NOT_PERMISSION);
		}

		List<ChatInfoWithMember> chatInfoWithMembers = chatReader.readChatInfoLists(chatRoomId);

		return new ChatGetServiceResponse(chatInfoWithMembers);
	}
}