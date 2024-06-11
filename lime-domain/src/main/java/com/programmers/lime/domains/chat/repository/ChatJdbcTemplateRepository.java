package com.programmers.lime.domains.chat.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.chat.domain.Chat;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatJdbcTemplateRepository {

	private static final String INSERT_SQL = "INSERT INTO chats (chat_room_id, member_id, message, send_at, chat_type) VALUES (?, ?, ?, ?, ?)";

	private final JdbcTemplate jdbcTemplate;

	@Transactional
	public void bulkInsertChats(final List<Chat> chats) {
		jdbcTemplate.batchUpdate(INSERT_SQL, chats, 50, this::prepareStatement);
	}

	private void prepareStatement(final PreparedStatement ps, final Chat chat) throws SQLException {
		ps.setLong(1, chat.getChatRoomId());
		ps.setLong(2, chat.getMemberId());
		ps.setString(3, chat.getMessage());
		ps.setTimestamp(4, Timestamp.valueOf(chat.getSendAt()));
		ps.setString(5, chat.getChatType().toString());
	}
}
