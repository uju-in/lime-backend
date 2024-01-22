package com.programmers.lime.global.config.security.auth.handler;

import static com.programmers.lime.domains.member.api.MemberController.*;
import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.vo.Role;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.domains.auth.CustomOauth2User;
import com.programmers.lime.global.config.security.jwt.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtService jwtService;
	private final MemberReader memberReader;

	@Override
	public void onAuthenticationSuccess(
		final HttpServletRequest request,
		final HttpServletResponse response,
		final Authentication authentication
	) {
		log.info("Oauth2 로그인 성공");

		try {
			CustomOauth2User oauth2User = (CustomOauth2User)authentication.getPrincipal();

			if (oauth2User.getRole() == Role.GUEST) {
				log.info("기본 인적사항은 업데이트 하지 않은 유저");
				signUpSuccess(response, oauth2User);
			} else {
				log.info("기본 인적사항이 등록된 유저");
				loginSuccess(response, oauth2User);
			}
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	private void signUpSuccess(
		final HttpServletResponse response,
		final CustomOauth2User oauth2User
	) {
		String accessToken = jwtService.generateAccessToken(String.valueOf(oauth2User.getMemberId()));

		sendAccessToken(response, accessToken);
	}

	private void loginSuccess(
		final HttpServletResponse response,
		final CustomOauth2User oauth2User
	) {
		Long memberId = oauth2User.getMemberId();
		String accessToken = jwtService.generateAccessToken(String.valueOf(memberId));
		String refreshToken = jwtService.generateRefreshToken();

		Member member = memberReader.read(memberId);

		sendAccessAndRefreshToken(response, member, accessToken, refreshToken);
	}

	private void sendAccessToken(
		final HttpServletResponse response,
		final String accessToken
	) {
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", "http://localhost:3000/join");
		try {
			response.sendRedirect("http://localhost:3000/join" + "?accessToken=" + accessToken);
		} catch (IOException e){
			throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		log.info("재발급된 Access Token : {}", accessToken);
	}

	private void sendAccessAndRefreshToken(
		final HttpServletResponse response,
		final Member member,
		final String accessToken,
		final String refreshToken
	){
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", "http://localhost:3000/");
		try {
			String redirectURL = URLEncoder.encode("http://localhost:3000/" + "?accessToken=" + accessToken
				+ "&memberId=" + member.getId() + "&nickname=" + member.getNickname(), "UTF-8");
			response.sendRedirect(redirectURL);
		} catch (IOException e){
			throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
		}

		final ResponseCookie cookie = ResponseCookie.from("refresh-token", refreshToken)
			.maxAge(COOKIE_AGE_SECONDS)
			.secure(true)
			.httpOnly(true)
			.sameSite("None")
			.path("/")
			.build();

		response.addHeader(SET_COOKIE, String.valueOf(cookie));

		log.info("Access Token, Refresh 설정 완료");
	}
}
