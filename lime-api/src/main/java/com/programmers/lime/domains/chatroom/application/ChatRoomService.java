package com.programmers.lime.domains.chatroom.application;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.programmers.lime.domains.chatroom.application.dto.response.ChatRoomGetServiceListResponse;
import com.programmers.lime.domains.chatroom.implementation.ChatRoomMemberAppender;
import com.programmers.lime.domains.chatroom.implementation.ChatRoomMemberReader;
import com.programmers.lime.domains.chatroom.implementation.ChatRoomMemberRemover;
import com.programmers.lime.domains.chatroom.implementation.ChatRoomReader;
import com.programmers.lime.domains.chatroom.model.ChatRoomInfo;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.config.security.SecurityUtils;
import com.programmers.lime.redis.chat.model.ChatRoomRemoveAllSessionInfo;
import com.programmers.lime.redis.chat.publisher.IChatRoomRemoveSessionPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomMemberReader chatRoomMemberReader;

	private final ChatRoomMemberAppender chatRoomMemberAppender;

	private final ChatRoomMemberRemover chatRoomMemberRemover;

	private final ChatRoomReader chatRoomReader;

	private final IChatRoomRemoveSessionPublisher removeSessionPublisher;

	public ChatRoomGetServiceListResponse getAvailableChatRooms() {
		Long memberId = SecurityUtils.getCurrentMemberId();

		List<ChatRoomInfo> chatRoomInfos = chatRoomReader.readOpenChatRoomsByMemberId(memberId);
		return new ChatRoomGetServiceListResponse(
			chatRoomInfos
		);
	}

	public void joinChatRoom(final Long chatRoomId) {

		Long memberId = SecurityUtils.getCurrentMemberId();

		if(Objects.isNull(memberId)) {
			throw new BusinessException(ErrorCode.MEMBER_ANONYMOUS);
		}

		if(chatRoomMemberReader.existMemberByMemberIdAndRoomId(chatRoomId, memberId)) {
			throw new BusinessException(ErrorCode.CHATROOM_ALREADY_JOIN);
		}

		chatRoomMemberAppender.appendChatRoomMember(chatRoomId, memberId);
	}

	public int countChatRoomMembersByChatRoomId(final Long chatRoomId) {
		return chatRoomMemberReader.countChatRoomMembersByChatRoomId(chatRoomId);
	}

	public void exitChatRoom(final Long chatRoomId) {
		Long memberId = SecurityUtils.getCurrentMemberId();
		ChatRoomRemoveAllSessionInfo chatRoomRemoveAllSessionInfo = ChatRoomRemoveAllSessionInfo.builder()
			.memberId(memberId)
			.roomId(chatRoomId)
			.build();

		removeSessionPublisher.removeAllSession("sub-chatroom-remove-session", chatRoomRemoveAllSessionInfo);
		chatRoomMemberRemover.removeChatRoomMember(chatRoomId, memberId);
	}
}
