package com.programmers.lime.domains.chat.repository;

import java.util.List;

import com.programmers.lime.domains.chat.model.ChatInfoWithMember;
import com.programmers.lime.domains.chat.model.ChatSummary;

public interface ChatRepositoryForCursor {

	List<ChatSummary> findAllByCursor(
		final Long chatRoomId,
		final String cursorId,
		final int pageSize
	);

}
