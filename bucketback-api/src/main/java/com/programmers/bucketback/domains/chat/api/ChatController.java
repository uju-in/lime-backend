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

	/**
	 * STOMP 프로토콜을 사용하여 채팅 메시지를 처리하고 구독자들에게 메시지를 전송하는 메소드.
	 *
	 * /publish/messages 경로로 메시지를 전송하면 이 메소드가 호출됩니다.
	 */
	@MessageMapping("/publish/messages")
	public void sendMessage(final ChatCreateRequest request) {
		LocalDateTime createdAt = LocalDateTime.now();
		ChatGetResponse chatGetResponse = new ChatGetResponse(
			request.message(),
			request.userNickname(),
			createdAt
		);

		/*
			SimpMessagingTemplate를 사용하여 구독자들에게 메시지를 전송합니다.
			메시지는 "/subscribe/rooms/{chatRoomId}" 주소로 전송되며,
			이는 해당 채팅방을 구독하는 클라이언트들에게 전달됩니다.
		*/
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
