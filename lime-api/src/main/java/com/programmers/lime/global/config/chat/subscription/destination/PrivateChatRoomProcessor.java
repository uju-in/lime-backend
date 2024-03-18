package com.programmers.lime.global.config.chat.subscription.destination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.redis.chat.ChatSessionRedisManager;
import com.programmers.lime.redis.chat.model.ChatSessionInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PrivateChatRoomProcessor implements DestinationProcessor {

	private static final Pattern pattern = Pattern.compile(".*/(\\d+)$");

	private final ChatSessionRedisManager chatSessionRedisManager;

	@Override
	public void process(StompHeaderAccessor accessor) {
		String sessionId = accessor.getSessionId();
		ChatSessionInfo sessionInfo = chatSessionRedisManager.getSessionInfo(sessionId);

		String destination = accessor.getDestination();
		Long roomId = extractRoomId(destination);

		String memberIdStr = accessor.getUser().getName();
		Long memberId = Long.valueOf(memberIdStr);

		if(!sessionInfo.memberId().equals(memberId) || !sessionInfo.roomId().equals(roomId)) {
			throw new BusinessException(ErrorCode.INVALID_SUBSCRIPTION_DESTINATION);
		}
	}

	public Long extractRoomId(String path) {
		Matcher matcher = pattern.matcher(path);

		if (matcher.find()) {
			String roomIdStr = matcher.group(1);
			return Long.valueOf(roomIdStr);
		}

		throw new BusinessException(ErrorCode.INVALID_SUBSCRIPTION_DESTINATION);
	}
}
