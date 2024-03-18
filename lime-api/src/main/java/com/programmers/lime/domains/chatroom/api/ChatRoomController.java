package com.programmers.lime.domains.chatroom.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.lime.domains.chatroom.api.dto.response.ChatRoomGetListResponse;
import com.programmers.lime.domains.chatroom.application.ChatRoomService;
import com.programmers.lime.domains.chatroom.application.dto.response.ChatRoomGetServiceListResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "chat-room", description = "채팅방 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

	private final ChatRoomService chatRoomService;

	//private final ChatService chatService;

	@Operation(summary = "채티방 전체 조회", description = "채팅방을 조회합니다.")
	@GetMapping
	public ResponseEntity<ChatRoomGetListResponse> getChatRooms() {
		ChatRoomGetServiceListResponse serviceResponse = chatRoomService.getAvailableChatRooms();
		ChatRoomGetListResponse response = ChatRoomGetListResponse.from(serviceResponse);

		return ResponseEntity.ok(
			response
		);
	}

	@Operation(summary = "채팅방 참여", description = "채팅방에 참여합니다.")
	@PostMapping("/{chatRoomId}/join")
	public ResponseEntity<Void> joinChatRoom(@PathVariable final Long chatRoomId) {
		chatRoomService.joinChatRoom(chatRoomId);
		//chatService.joinChatRoom(chatRoomId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "채팅방 인원수 조회", description = "채팅방의 인원수를 조회합니다.")
	@GetMapping("/{chatRoomId}/members/count")
	public ResponseEntity<Integer> countChatRoomMembers(@PathVariable final Long chatRoomId) {
		return ResponseEntity.ok(
			chatRoomService.countChatRoomMembersByChatRoomId(chatRoomId)
		);
	}

	@Operation(summary = "채팅방 나가기", description = "채팅방에서 나갑니다.")
	@DeleteMapping("/{chatRoomId}/exit")
	public ResponseEntity<Void> exitChatRoom(@PathVariable final Long chatRoomId) {
		chatRoomService.exitChatRoom(chatRoomId);
		//chatService.sendExitMessageToChatRoom(chatRoomId);
		return ResponseEntity.ok().build();
	}
}
