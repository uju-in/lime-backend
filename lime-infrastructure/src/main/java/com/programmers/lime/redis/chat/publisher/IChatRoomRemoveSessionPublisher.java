package com.programmers.lime.redis.chat.publisher;

import com.programmers.lime.redis.chat.model.ChatRoomRemoveAllSessionInfo;

public interface IChatRoomRemoveSessionPublisher {
	void removeAllSession(String channel, ChatRoomRemoveAllSessionInfo chatRoomRemoveAllSessionInfo);
}
