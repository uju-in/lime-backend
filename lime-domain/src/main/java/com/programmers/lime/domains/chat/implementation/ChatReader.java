package com.programmers.lime.domains.chat.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.domains.chat.model.ChatSummary;
import com.programmers.lime.domains.chat.repository.ChatRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatReader {

	private final ChatRepository chatRepository;
	private static final int DEFAULT_SIZE = 20;

	public CursorSummary<ChatSummary> readByCursor(
		final Long chatRoomId,
		final CursorPageParameters parameters
	) {
		List<ChatSummary> summaries = chatRepository.findAllByCursor(
			chatRoomId,
			parameters.cursorId(),
			getPageSize(parameters)
		);

		return CursorUtils.getCursorSummaries(summaries);
	}

	private int getPageSize(final CursorPageParameters parameters) {
		Integer parameterSize = parameters.size();

		if (parameterSize == null) {
			return DEFAULT_SIZE;
		}

		return parameterSize;
	}

	public ChatSummary readFirstChat(final Long chatRoomId) {
		return chatRepository.findFirstByCursor(chatRoomId);
	}

	public ChatSummary readLastChat(final Long chatRoomId) {
		return chatRepository.findLastByCursor(chatRoomId);
	}

}
