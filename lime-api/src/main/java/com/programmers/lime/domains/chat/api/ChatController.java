package com.programmers.lime.domains.chat.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.lime.domains.chat.api.dto.response.ChatGetResponse;
import com.programmers.lime.domains.chat.application.ChatService;
import com.programmers.lime.domains.chat.application.dto.response.ChatGetServiceResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "chat", description = "채팅 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {

	private final ChatService chatService;

	@Operation(summary = "채팅 조회", description = "chatRoomId을 이용하여 채팅을 조회")
	@GetMapping("/{chatRoomId}")
	public ResponseEntity<ChatGetResponse> getChatInfoLists(@PathVariable final Long chatRoomId) {
		ChatGetServiceResponse serviceResponse = chatService.getChatWithMemberList(chatRoomId);
		ChatGetResponse response = ChatGetResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}
}
