package com.programmers.lime.global.config.chat.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.config.security.jwt.JwtService;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthTokenSetPreHandler implements ChannelInterceptor {

	private static final String JWT_PREFIX = "Bearer ";
	private static final String AUTH_HEADER_KEY = "Authorization";

	private final JwtService jwtService;

	private final UserDetailsService userDetailsService;

	/**
	 * 클라이언트가 서버와 연결할 때, 제일 먼저 실행되어야 하는 인터셉터 입니다.
	 * JWT 토큰을 검증하고, 사용자 정보를 SecurityContextHolder에 저장합니다.
	 */
	@Override
	public Message<?> preSend(
		final Message<?> message,
		final org.springframework.messaging.MessageChannel channel
	) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if (accessor == null) {
			throw new MalformedJwtException(ErrorCode.MEMBER_NOT_LOGIN.getMessage());
		}

		SimpMessageType messageType = accessor.getMessageType();
		if(messageType == SimpMessageType.HEARTBEAT) {
			return message;
		}

		StompCommand command = accessor.getCommand();
		// JWT 토큰이 필요한 명령어에 대해서만 토큰을 검증합니다.
		if(
			command.equals(StompCommand.SEND) ||
			command.equals(StompCommand.CONNECT) ||
			command.equals(StompCommand.SUBSCRIBE)
		) {
			try{
				setAuthTokenByAccessor(accessor);
			} catch (JwtException e) {
				throw new MalformedJwtException(ErrorCode.INVALID_ACCESS_TOKEN.getMessage());
			}
		}

		return message;
	}

	private void setAuthTokenByAccessor(final StompHeaderAccessor accessor) {
		final String jwt;
		final String memberId;
		final String authHeader;

		authHeader = accessor.getFirstNativeHeader(AUTH_HEADER_KEY);

		if (authHeader == null || !authHeader.startsWith(JWT_PREFIX)) {
			throw new MalformedJwtException(ErrorCode.MEMBER_NOT_LOGIN.getMessage());
		}

		jwt = authHeader.substring(JWT_PREFIX.length());

		memberId = getMemberIdByJWT(jwt);

		final var authToken = getAuthTokenByMemberId(memberId);

		accessor.setUser(authToken);
	}

	private UsernamePasswordAuthenticationToken getAuthTokenByMemberId(final String memberId) {
		final UserDetails userDetails = this.userDetailsService.loadUserByUsername(memberId);

		return new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities()
		);
	}

	private String getMemberIdByJWT(final String jwt) {
		final String memberId;

		if(!jwtService.isAccessTokenValid(jwt)) {
			throw new MalformedJwtException(ErrorCode.INVALID_ACCESS_TOKEN.getMessage());
		}

		memberId = jwtService.extractUsername(jwt);

		return memberId;
	}

}
