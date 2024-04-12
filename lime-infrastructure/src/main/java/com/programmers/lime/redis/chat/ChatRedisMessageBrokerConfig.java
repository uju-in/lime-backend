package com.programmers.lime.redis.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.programmers.lime.redis.chat.listener.ChatMessageListenerImpl;
import com.programmers.lime.redis.chat.listener.ChatRoomRemoveAllSessionListenerImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ChatRedisMessageBrokerConfig {

	private final ChatMessageListenerImpl chatMessageListener;
	private final ChatRoomRemoveAllSessionListenerImpl chatRoomRemoveAllSessionListener;
	private final RedisConnectionFactory connectionFactory;

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(addMessageListener(), chatChannelTopic());

		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(removeSessionListenerAdapter(), removeSessionChannelTopic());
		return container;
	}

	public MessageListenerAdapter addMessageListener() {
		return new MessageListenerAdapter(chatMessageListener, "onMessage");
	}

	public ChannelTopic chatChannelTopic() {
		return new ChannelTopic("sub-chat");
	}

	public MessageListenerAdapter removeSessionListenerAdapter() {
		return new MessageListenerAdapter(chatRoomRemoveAllSessionListener, "onMessage");
	}

	public ChannelTopic removeSessionChannelTopic() {
		return new ChannelTopic("sub-chatroom-remove-session");
	}
}
