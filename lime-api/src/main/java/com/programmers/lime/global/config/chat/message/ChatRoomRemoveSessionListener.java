package com.programmers.lime.global.config.chat.message;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.config.chat.WebSocketSessionManager;
import com.programmers.lime.redis.chat.ChatSessionRedisManager;
import com.programmers.lime.redis.chat.listener.IChatRoomRemoveSessionListener;
import com.programmers.lime.redis.chat.model.ChatRoomRemoveAllSessionInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatRoomRemoveSessionListener implements IChatRoomRemoveSessionListener {

	private final ChatSessionRedisManager chatSessionRedisManager;

	private final WebSocketSessionManager webSocketSessionManager;

	@Override
	public void removeAllSession(final ChatRoomRemoveAllSessionInfo chatRoomRemoveAllSessionInfo) {

		Set<String> sessionIdsByMemberAndRoom = chatSessionRedisManager.getSessionIdsByMemberAndRoom(
			chatRoomRemoveAllSessionInfo.memberId(),
			chatRoomRemoveAllSessionInfo.roomId()
		);

		for (String memberSessionId : sessionIdsByMemberAndRoom) {
			try {
				webSocketSessionManager.closeSession(memberSessionId);
			} catch (Exception e) {
				throw new BusinessException(ErrorCode.CHAT_SESSION_NOT_FOUND);
			}
		}
	}
}
