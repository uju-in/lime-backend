package com.programmers.lime.redis.chat.listener;

import com.programmers.lime.redis.chat.model.ChatRoomRemoveAllSessionInfo;

public interface IChatRoomRemoveSessionListener {

	void removeAllSession(final ChatRoomRemoveAllSessionInfo chatRoomRemoveAllSessionInfo);
}
