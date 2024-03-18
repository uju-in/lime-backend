package com.programmers.lime.domains.chatroom.application;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.programmers.lime.domains.chatroom.application.dto.response.ChatRoomGetServiceListResponse;
import com.programmers.lime.domains.chatroom.implementation.ChatRoomMemberAppender;
import com.programmers.lime.domains.chatroom.implementation.ChatRoomMemberReader;
import com.programmers.lime.domains.chatroom.implementation.ChatRoomMemberRemover;
import com.programmers.lime.domains.chatroom.implementation.ChatRoomReader;
import com.programmers.lime.domains.chatroom.model.ChatRoomInfo;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.config.chat.WebSocketSessionManager;
import com.programmers.lime.global.config.security.SecurityUtils;
import com.programmers.lime.redis.chat.ChatSessionRedisManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomMemberReader chatRoomMemberReader;

	private final ChatRoomMemberAppender chatRoomMemberAppender;

	private final ChatRoomMemberRemover chatRoomMemberRemover;

	private final ChatRoomReader chatRoomReader;

	private final WebSocketSessionManager webSocketSessionManager;

	private final ChatSessionRedisManager chatSessionRedisManager;

	public ChatRoomGetServiceListResponse getAvailableChatRooms() {
		Long memberId = SecurityUtils.getCurrentMemberId();

		List<ChatRoomInfo> chatRoomInfos = null;

		if(Objects.isNull(memberId)) {
			chatRoomInfos = chatRoomReader.readOpenChatRooms();
		} else {
			chatRoomInfos = chatRoomReader.readOpenChatRoomsByMemberId(memberId);
		}

		return new ChatRoomGetServiceListResponse(
			chatRoomInfos
		);
	}

	public void joinChatRoom(Long chatRoomId) {

		Long memberId = SecurityUtils.getCurrentMemberId();

		if(Objects.isNull(memberId)) {
			throw new BusinessException(ErrorCode.MEMBER_ANONYMOUS);
		}

		if(chatRoomMemberReader.existMemberByMemberIdAndRoomId(chatRoomId, memberId)) {
			throw new BusinessException(ErrorCode.CHATROOM_ALREADY_JOIN);
		}

		chatRoomMemberAppender.appendChatRoomMember(chatRoomId, memberId);
	}

	public int countChatRoomMembersByChatRoomId(Long chatRoomId) {
		return chatRoomMemberReader.countChatRoomMembersByChatRoomId(chatRoomId);
	}

	public void exitChatRoom(Long chatRoomId) {
		Long memberId = SecurityUtils.getCurrentMemberId();

		Set<String> sessionIdsByMemberAndRoom = chatSessionRedisManager.getSessionIdsByMemberAndRoom(
			memberId,
			chatRoomId
		);

		for(String memberSessionId : sessionIdsByMemberAndRoom) {
			try {
				webSocketSessionManager.closeSession(memberSessionId);
			} catch (Exception e) {
				throw new BusinessException(ErrorCode.CHAT_SESSION_NOT_FOUND);
			}
		}

		chatRoomMemberRemover.removeChatRoomMember(chatRoomId, memberId);
	}
}
