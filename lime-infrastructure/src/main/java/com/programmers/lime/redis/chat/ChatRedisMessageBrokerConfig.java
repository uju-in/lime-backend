package com.programmers.lime.redis.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.programmers.lime.redis.chat.listener.RedisChatListener;

@Configuration
public class ChatRedisMessageBrokerConfig {

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(
		final RedisConnectionFactory connectionFactory,
		final MessageListenerAdapter listenerAdapter,
		final ChannelTopic channelTopic
	) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, channelTopic);
		return container;
	}

	@Bean
	public MessageListenerAdapter listenerAdapter(final RedisChatListener listener) {
		return new MessageListenerAdapter(listener, "onMessage");
	}

	@Bean
	public ChannelTopic channelTopic() {
		return new ChannelTopic("sub-chat");
	}
}
