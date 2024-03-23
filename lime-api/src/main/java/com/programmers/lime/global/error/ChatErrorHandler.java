package com.programmers.lime.global.error;

import java.nio.charset.StandardCharsets;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import com.programmers.lime.error.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatErrorHandler extends StompSubProtocolErrorHandler {

	private static final String ERROR_CODE_PREFIX = "Error Message: ";

	public ChatErrorHandler() {
		super();
	}

	@Override
	public Message<byte[]> handleClientMessageProcessingError(final Message<byte[]> clientMessage, final Throwable ex) {

		log.info("handleClientMessageProcessingError: {}", ex.getMessage());

		for (ErrorCode errorCode : ErrorCode.values()) {

			if (ex.getCause() == null) {
				return super.handleClientMessageProcessingError(clientMessage, ex);
			}

			if (ex.getCause().getMessage().equals(errorCode.getMessage())) {
				return prepareErrorMessage(errorCode);
			}
		}

		return super.handleClientMessageProcessingError(clientMessage, ex);
	}

	private Message<byte[]> prepareErrorMessage(final ErrorCode errorCode) {

		String retErrorCodeMessage = ERROR_CODE_PREFIX + errorCode.getMessage();
		StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

		return MessageBuilder.createMessage(
			retErrorCodeMessage.getBytes(StandardCharsets.UTF_8),
			accessor.getMessageHeaders()
		);
	}

}