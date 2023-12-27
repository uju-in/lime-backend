package com.programmers.bucketback.domains.chat.api;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.programmers.bucketback.domains.chat.api.dto.request.ChatCreateRequest;
import com.programmers.bucketback.domains.chat.api.dto.response.ChatGetResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

	private final SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/publish/messages")
	public void sendMessage(final ChatCreateRequest request) {
		LocalDateTime createdAt = LocalDateTime.now();
		ChatGetResponse chatGetResponse = new ChatGetResponse(
			request.message(),
			request.userNickname(),
			createdAt
		);

		simpMessagingTemplate.convertAndSend("/subscribe/rooms/" + request.chatRoomId(), chatGetResponse);
	}

	@GetMapping("/chat/{roomId}")
	public String chatPage(
		@PathVariable("roomId") final int roomId,
		final Model model
	) {
		model.addAttribute("roomId", roomId);

		return "chat";
	}

	@GetMapping("/chat-rooms")
	public String chatRoomPage() {
		return "chat-rooms";
	}
}
