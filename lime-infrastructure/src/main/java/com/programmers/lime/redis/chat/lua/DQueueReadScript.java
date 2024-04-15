package com.programmers.lime.redis.chat.lua;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import jakarta.annotation.PostConstruct;

@Component
public class DQueueReadScript {

	private String readByDQueueScript;

	@PostConstruct
	public void init() {
		try {
			ClassPathResource cpr = new ClassPathResource("ReadByDQueue.lua");
			byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
			readByDQueueScript = new String(bdata, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load redis script", e);
		}
	}

	public byte[] getReadByLinkedListScriptBytes() {
		return readByDQueueScript.getBytes(StandardCharsets.UTF_8);
	}
}
