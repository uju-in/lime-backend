package com.programmers.lime.domains.chat.application;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.domains.chat.application.dto.response.ChatGetCursorServiceResponse;
import com.programmers.lime.domains.chat.implementation.ChatAppender;
import com.programmers.lime.domains.chat.implementation.ChatReader;
import com.programmers.lime.domains.chat.model.ChatInfoWithMember;
import com.programmers.lime.domains.chat.model.ChatSummary;
import com.programmers.lime.domains.chat.model.ChatType;
import com.programmers.lime.domains.chatroom.implementation.ChatRoomMemberReader;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.config.security.SecurityUtils;
import com.programmers.lime.global.event.chat.ChatSendMessageEvent;
import com.programmers.lime.global.event.chat.ChatAppendCacheEvent;
import com.programmers.lime.redis.chat.ChatCursorCacheReader;
import com.programmers.lime.redis.chat.ChatSessionRedisManager;
import com.programmers.lime.redis.chat.model.ChatCursorCache;
import com.programmers.lime.redis.chat.model.ChatCursorCacheResult;
import com.programmers.lime.redis.chat.model.ChatCursorCacheStatus;
import com.programmers.lime.redis.chat.model.ChatSessionInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final MemberReader memberReader;

	private final ChatSessionRedisManager chatSessionRedisManager;

	private final ChatReader chatReader;

	private final ChatAppender chatAppender;

	private final ChatRoomMemberReader chatRoomMemberReader;

	private final ApplicationEventPublisher applicationEventPublisher;
  
	private final static int DEFAULT_CURSOR_SIZE = 20;

	private final ChatCursorCacheReader chatCursorCacheReader;

	public void sendMessage(final Long memberId, final String sessionId, final String message, final String timeSeq) {
		Member member = memberReader.read(memberId);

		ChatSessionInfo sessionInfo = chatSessionRedisManager.getSessionInfo(sessionId);
		Long chatRoomId = sessionInfo.roomId();

		if (!memberId.equals(sessionInfo.memberId())) {
			throw new BusinessException(ErrorCode.CHAT_NOT_PERMISSION);
		}

		ChatInfoWithMember chatInfoWithMember = ChatInfoWithMember.builder()
			.message(message)
			.chatRoomId(chatRoomId)
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.memberId(member.getId())
			.chatType(ChatType.CHAT)
			.sendAt(getCreatedAt(timeSeq))
			.build();

		chatAppender.appendChat(chatInfoWithMember.toChatInfo());

		applicationEventPublisher.publishEvent(
			new ChatSendMessageEvent("/subscribe/rooms/" + chatRoomId, chatInfoWithMember)
		);
	}

	public void joinChatRoom(final Long chatRoomId) {
		Long memberId = SecurityUtils.getCurrentMemberId();
		Member member = memberReader.read(memberId);

		String message = member.getNickname() + "님이 참여하셨습니다.";

		String timeSeq = String.valueOf(System.currentTimeMillis());
		ChatInfoWithMember chatInfoWithMember = ChatInfoWithMember.builder()
			.message(message)
			.chatRoomId(chatRoomId)
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.memberId(member.getId())
			.chatType(ChatType.JOIN)
			.sendAt(getCreatedAt(timeSeq))
			.build();

		chatAppender.appendChat(chatInfoWithMember.toChatInfo());

		applicationEventPublisher.publishEvent(
			new ChatSendMessageEvent("/subscribe/rooms/" + chatRoomId, chatInfoWithMember)
		);
	}

	public void sendExitMessageToChatRoom(final Long chatRoomId) {
		Long memberId = SecurityUtils.getCurrentMemberId();
		Member member = memberReader.read(memberId);

		String message = member.getNickname() + "님이 퇴장하셨습니다.";

		String timeSeq = String.valueOf(System.currentTimeMillis());

		ChatInfoWithMember chatInfoWithMember = ChatInfoWithMember.builder()
			.message(message)
			.chatRoomId(chatRoomId)
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.memberId(member.getId())
			.chatType(ChatType.EXIT)
			.sendAt(getCreatedAt(timeSeq))
			.build();

		chatAppender.appendChat(chatInfoWithMember.toChatInfo());

		applicationEventPublisher.publishEvent(
			new ChatSendMessageEvent("/subscribe/rooms/" + chatRoomId, chatInfoWithMember)
		);
	}

	@Cacheable(value = "chat", key = "#chatRoomId + '_' + #parameters.cursorId + '_' + #parameters.size")
	public ChatGetCursorServiceResponse getChatByCursor(
		final Long chatRoomId,
		final CursorPageParameters parameters
	) {

		Long memberId = SecurityUtils.getCurrentMemberId();

		if (!chatRoomMemberReader.existMemberByMemberIdAndRoomId(chatRoomId, memberId)) {
			throw new BusinessException(ErrorCode.CHATROOM_NOT_PERMISSION);
		}

		CursorSummary<ChatSummary> summaries = readByCursorInCacheOrDb(chatRoomId, parameters);

		return new ChatGetCursorServiceResponse(summaries);
	}

	/*
		Todo: 컨트롤러 레이어와 비즈니스 서비스 레이어 모듈 분리 후 ChatCursorCacheManager 관련 코드 삭제 예정
		ChatCursorCacheManager 관련 코드는 구현 레이어 코드로 분리되어야 함
	 */
	private CursorSummary<ChatSummary> readByCursorInCacheOrDb(
		final Long chatRoomId,
		final CursorPageParameters parameters
	) {
		int pageSize = parameters.size() == null ? DEFAULT_CURSOR_SIZE : parameters.size();

		ChatCursorCacheResult cursorRedisResult = chatCursorCacheReader.readByCursor(
			chatRoomId,
			parameters.cursorId(),
			pageSize
		);

		if (cursorRedisResult.chatCursorCacheStatus() == ChatCursorCacheStatus.FAIL) {
			CursorSummary<ChatSummary> chatSummaryCursorSummary = chatReader.readByCursor(chatRoomId, parameters);

			applicationEventPublisher.publishEvent(
				ChatAppendCacheEvent.builder()
					.chatRoomId(chatRoomId)
					.startCursorId(parameters.cursorId())
					.summaries(chatSummaryCursorSummary.summaries())
					.requestSize(pageSize)
					.build()
			);

			return chatSummaryCursorSummary;
		} else {
			return dataToSummary(cursorRedisResult);
		}
	}

	private CursorSummary<ChatSummary> dataToSummary(final ChatCursorCacheResult cursorRedisResult) {
		List<ChatSummary> summaries = cursorRedisResult.chatCursorCacheList().stream()
			.map(this::getChatSummary)
			.toList();

		return CursorUtils.getCursorSummaries(summaries);
	}

	private LocalDateTime getCreatedAt(final String timeSeq) {
		long longTimeSeq = Long.parseLong(timeSeq);
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(longTimeSeq), ZoneId.systemDefault());
	}

	private ChatSummary getChatSummary(final ChatCursorCache data) {
		return new ChatSummary(
			data.cursorId(),
			data.chatId(),
			data.chatRoomId(),
			data.memberId(),
			data.nickname(),
			data.profileImage(),
			data.message(),
			LocalDateTime.parse(data.sendAt()),
			ChatType.valueOf(data.chatType())
		);
	}
}