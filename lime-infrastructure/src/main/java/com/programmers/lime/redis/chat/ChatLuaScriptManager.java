package com.programmers.lime.redis.chat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import jakarta.annotation.PostConstruct;

@Component
public class ChatLuaScriptManager {

	private static String readBylinkedListScript;

	@PostConstruct
	public void init() {
		try {
			ClassPathResource cpr = new ClassPathResource("ReadByLinkedList.lua");
			byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
			readBylinkedListScript = new String(bdata, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load redis script", e);
		}
	}

	public static String getReadByLinkedListScript() {
		return readBylinkedListScript;
	}
}
