package com.programmers.lime.domains.chat.repository;

import java.util.List;

import com.programmers.lime.domains.chat.model.ChatInfoWithMember;

public interface ChatQueryDsl {

	List<ChatInfoWithMember> getChatInfoWithMembers(Long chatRoomId);

}
