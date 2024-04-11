package com.programmers.lime.domains.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.chat.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long>, ChatRepositoryForCursor {
}
