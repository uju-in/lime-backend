package com.programmers.lime.global.config.security.auth.handler;

import static com.programmers.lime.domains.member.api.MemberController.*;
import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.lime.domains.member.api.dto.response.MemberLoginResponse;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.vo.Role;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.config.security.auth.CustomOauth2User;
import com.programmers.lime.global.config.security.jwt.JwtService;

import jakarta.servlet.ServletException;
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

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationSuccess(
		final HttpServletRequest request,
		final HttpServletResponse response,
		final Authentication authentication
	) throws IOException, ServletException {
		log.info("Oauth2 로그인 성공");

		try{
			CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();

			if(oauth2User.getRole() == Role.GUEST) {
				log.info("기본 인적사항은 업데이트 하지 않은 유저");
				String accessToken = jwtService.generateAccessToken(String.valueOf(oauth2User.getMemberId()));
				response.addHeader("Authorization", "Bearer " + accessToken);
				response.sendRedirect("/api/members/profile"); //인적사항을 입력하는 곳으로 리다이렉트시킴
				jwtService.sendAccessToken(response, accessToken);
			} else{
				log.info("기본 인적사항이 등록된 유저");
				loginSuccess(response, oauth2User);
			}
		} catch (Exception e){
			throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	private void loginSuccess(
		final HttpServletResponse response,
		final CustomOauth2User oauth2User
	) throws IOException {
		Long memberId = oauth2User.getMemberId();
		String accessToken = jwtService.generateAccessToken(String.valueOf(memberId));
		String refreshToken = jwtService.generateRefreshToken();

		final ResponseCookie cookie = ResponseCookie.from("refresh-token", refreshToken)
			.maxAge(COOKIE_AGE_SECONDS)
			.secure(true)
			.httpOnly(true)
			.sameSite("None")
			.path("/")
			.build();

		Member member = memberReader.read(memberId);
		MemberLoginResponse loginResponse = MemberLoginResponse.from(member,accessToken);
		String result = objectMapper.writeValueAsString(loginResponse);
		response.getWriter().write(result);

		response.addHeader("Authorization", "Bearer " + accessToken);
		response.addHeader(SET_COOKIE, String.valueOf(cookie));
		jwtService.sendAccessAndRefreshToken(response, accessToken);
	}

}
