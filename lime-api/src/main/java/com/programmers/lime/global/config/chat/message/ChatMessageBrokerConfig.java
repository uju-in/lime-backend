package com.programmers.lime.global.config.chat.message;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.programmers.lime.redis.chat.listener.IChatListener;
import com.programmers.lime.redis.chat.publisher.IChatPublisher;
import com.programmers.lime.redis.chat.publisher.RedisChatPublisher;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ChatMessageBrokerConfig {

	private final SimpleChatListener simpleChatListener;

	private final RedisChatPublisher redisChatPublisher;

	@Bean
	@Primary
	public IChatListener configListener() {
		return simpleChatListener;
	}

	@Bean
	@Primary
	public IChatPublisher configPublisher() {
		return redisChatPublisher;
	}
}
