package com.programmers.lime.domains.chatroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.chatroom.domain.ChatRoom;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomQueryDsl{



}
