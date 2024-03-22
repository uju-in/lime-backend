package com.programmers.lime.domains.chat.api;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.programmers.lime.domains.chat.api.dto.request.ChatCreateRequest;
import com.programmers.lime.domains.chat.application.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

	private final ChatService chatService;

	@MessageMapping("/publish/rooms")
	public void sendMessage(
		@Payload final ChatCreateRequest request,
		final Principal principal,
		SimpMessageHeaderAccessor headerAccessor
	) {
		Long currentMemberId = Long.valueOf(principal.getName());
		String sessionId = headerAccessor.getSessionId();
		String timeSeq = headerAccessor.getFirstNativeHeader("time-seq");

		chatService.sendMessage(currentMemberId, sessionId, request.message(), timeSeq);
	}
}
