package com.programmers.lime.redis.chat.listener;

import com.programmers.lime.redis.chat.model.ChatInfoWithMemberCache;

public interface IChatListener {
	void sendMessage(final ChatInfoWithMemberCache chatInfoWithMemberCache);
}
