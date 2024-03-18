package com.programmers.lime.global.config.chat.handler;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chatroom.implementation.ChatRoomMemberReader;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.config.chat.type.MessageDomainType;
import com.programmers.lime.global.event.chat.ChatSessionAddEvent;

import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SessionAddPreHandler implements ChannelInterceptor{

	private final ApplicationEventPublisher eventPublisher;

	private final ChatRoomMemberReader chatRoomMemberReader;

	/**
	 * 클라이언트가 서버와 연결할 때, 클라이언트의 요청을 처리하기 전에 실행됩니다.
	 * chat을 하기위해 특정 chat room에 대한 연결 요청인 경우, 해당 chat room에 대한 권한이 있는지 확인합니다.
	 */
	@Override
	public Message<?> preSend(
		final Message<?> message,
		final org.springframework.messaging.MessageChannel channel
	) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		SimpMessageType messageType = accessor.getMessageType();

		if(messageType == SimpMessageType.HEARTBEAT) {
			return message;
		}
		StompCommand command = accessor.getCommand();
		if(command != StompCommand.CONNECT) {
			return message;
		}

		// 지정된 MessageDomainType이 없다면 예외를 발생시킵니다.
		MessageDomainType messageDomainType = getMessageDomainType(accessor);

		if(messageDomainType == MessageDomainType.CHAT) {
			String memberIdStr = accessor.getUser().getName();
			Long memberId = Long.valueOf(memberIdStr);

			String roomIdHeader = accessor.getFirstNativeHeader("ROOM-ID");
			Long chatRoomId = Long.valueOf(roomIdHeader);

			boolean result = chatRoomMemberReader.existMemberByMemberIdAndRoomId(
				chatRoomId,
				memberId
			);

			if(!result) {
				throw new MalformedJwtException(ErrorCode.MEMBER_NOT_LOGIN.getMessage());
			}

			String sessionId = accessor.getSessionId();

			eventPublisher.publishEvent(new ChatSessionAddEvent(sessionId, memberIdStr, roomIdHeader));
		}

		return message;
	}

	private MessageDomainType getMessageDomainType(final StompHeaderAccessor accessor) {
		String messageDomainTypeHeader = accessor.getFirstNativeHeader("message-domain-type");

		MessageDomainType messageDomainType = null;
		try {
			messageDomainType = MessageDomainType.valueOf(messageDomainTypeHeader.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new MalformedJwtException(ErrorCode.MESSAGE_DOMAIN_TYPE_NOT_FOUND.getMessage());
		}

		return messageDomainType;
	}
}
