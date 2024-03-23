package com.programmers.lime.domains.chatroom.repository;

import java.util.List;

import com.programmers.lime.domains.chatroom.model.ChatRoomInfo;

public interface ChatRoomQueryDsl {

	List<ChatRoomInfo> findOpenChatRoomsIncludingWithoutMembers(Long memberId);

	List<ChatRoomInfo> findOpenChatRooms();
}
