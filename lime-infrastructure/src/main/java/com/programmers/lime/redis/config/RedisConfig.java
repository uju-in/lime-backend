package com.programmers.lime.redis.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import jakarta.annotation.PostConstruct;

@Configuration
@ConfigurationProperties("spring.redis.cluster")
public class RedisConfig {

	@Value("${spring.data.redis.cluster.nodes}")
	private String clusterNodesString;

	@Value("${spring.data.redis.password}")
	private String password;

	private List<String> clusterNodes;

	@PostConstruct
	public void init() {
		clusterNodes = Arrays.asList(clusterNodesString.split(","));
	}

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(clusterNodes);
		clusterConfiguration.setPassword(RedisPassword.of(password));

		return new LettuceConnectionFactory(clusterConfiguration);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory());

		return redisTemplate;
	}
}
