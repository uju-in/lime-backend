package com.programmers.lime.redis.chat.listener;

import org.springframework.stereotype.Component;

import com.programmers.lime.redis.chat.model.ChatInfoWithMemberCache;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatListenerProxy implements IChatListener {

	private final IChatListener chatListener;

	public void sendMessage(final String destination, final ChatInfoWithMemberCache chatInfoWithMemberCache) {
		chatListener.sendMessage(destination, chatInfoWithMemberCache);
	}
}
