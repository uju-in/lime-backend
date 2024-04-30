package com.programmers.lime.domains.chat.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.lime.domains.chat.api.dto.response.ChatGetByCursorResponse;
import com.programmers.lime.domains.chat.application.ChatService;
import com.programmers.lime.domains.chat.application.dto.response.ChatGetCursorServiceResponse;
import com.programmers.lime.global.cursor.CursorRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "chat", description = "채팅 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {

	private final ChatService chatService;

	@Operation(summary = "채팅 목록 조회", description = "채팅방 id를 이용하여 채팅 목록 조회")
	@GetMapping("/{chatRoomId}")
	public ResponseEntity<ChatGetByCursorResponse> getChatInfoLists(
		@Schema(description = "채팅방 id", example = "1")
		@PathVariable final Long chatRoomId,
		@ModelAttribute @Valid final CursorRequest cursorRequest
	) {
		ChatGetCursorServiceResponse serviceResponse = chatService.getChatByCursor(chatRoomId,
			cursorRequest.toParameters());
		ChatGetByCursorResponse response = ChatGetByCursorResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}
}
