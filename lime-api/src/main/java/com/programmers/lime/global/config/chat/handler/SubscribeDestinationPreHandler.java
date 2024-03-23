package com.programmers.lime.global.config.chat.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.config.chat.subscription.destination.PrivateChatRoomProcessor;
import com.programmers.lime.global.config.chat.type.DestinationEnum;
import com.programmers.lime.global.config.chat.type.DestinationType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubscribeDestinationPreHandler implements ChannelInterceptor {

	private final PrivateChatRoomProcessor privateChatRoomProcessor;
	private final StompHandlerManager stompHandlerManager;

	/**
	 * 클라이언트가 구독할 때, 클라이언트의 요청을 처리하기 전에 실행됩니다.
	 * 구독하고자 하는 destination에 대한 유효성 검사를 수행합니다.
	 * 추가적으로 destination에 따라 검증 작업을 수행합니다.
	 */
	@Override
	public Message<?> preSend(
		final Message<?> message,
		final org.springframework.messaging.MessageChannel channel
	) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		StompCommand command = stompHandlerManager.getCommand(accessor);

		if (command != StompCommand.SUBSCRIBE) {
			return message;
		}

		String destination = accessor.getDestination();
		if (destination == null) {
			throw new BusinessException(ErrorCode.INVALID_SUBSCRIPTION_DESTINATION);
		}

		DestinationEnum destinationEnum = DestinationEnum.findByPath(destination);

		if (destinationEnum.getType() == DestinationType.PRIVATE_CHAT_ROOM) {
			privateChatRoomProcessor.process(accessor);
		}

		return message;
	}
}
