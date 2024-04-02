package com.programmers.lime.redis.chat.publisher;

import com.programmers.lime.redis.chat.model.ChatInfoWithMemberCache;

public interface IChatPublisher {
	void sendMessage(String channel, ChatInfoWithMemberCache chatInfoWithMemberCache);
}
