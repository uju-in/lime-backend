package com.programmers.bucketback.global.config.security.jwt;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.MemberBuilder;

import io.jsonwebtoken.JwtException;

class JwtServiceTest {

	private String accessSecretKey = "testsecretkeydaslkjslsdnmflsdkgjsldgkjlweptojkgn";
	private String refreshSecretKey = "testsecretkeydaslkjslsdnmflsdkgjsldgkjlweptojkgn";
	private JwtConfig jwtConfig = new JwtConfig(accessSecretKey, 100, refreshSecretKey, 100);
	private JwtService jwtService = new JwtService(jwtConfig);

	@Test
	@DisplayName("access token 생성에 성공한다.")
	void successAccessTokenCreate() {
		//given
		Long memberId = 1L;
		Member member = MemberBuilder.build(memberId);

		//when
		String token = jwtService.generateAccessToken(String.valueOf(member.getId()));
		String username = jwtService.extractUsername(token);

		//then
		assertThat(jwtService.isAccessTokenValid(token)).isEqualTo(true);
		assertThat(username).isEqualTo(String.valueOf(member.getId()));
	}

	@Nested
	@DisplayName("유효한 토큰 유효성 검사")
	class ValidAccessToken {

		@ParameterizedTest
		@ValueSource(ints = {1, 10, 1000})
		@DisplayName("유효한 토큰 검사에 성공한다.")
		void successValidTokenEvaluation(int expirationSecond) {
			//given
			JwtConfig jwtConfig = new JwtConfig(accessSecretKey, expirationSecond, refreshSecretKey, 100);
			JwtService jwtService = new JwtService(jwtConfig);

			Long memberId = 1L;
			Member member = MemberBuilder.build(memberId);
			String token = jwtService.generateAccessToken(String.valueOf(member.getId()));

			//given
			boolean result = jwtService.isAccessTokenValid(token);
			assertThat(result).isEqualTo(true);
		}

		@ParameterizedTest
		@ValueSource(ints = {0})
		@DisplayName("만료된 토큰 검증에 성공한다.")
		void successExpiredTokenEvaluation(int expirationSecond) {
			//given
			JwtConfig jwtConfig = new JwtConfig(accessSecretKey, expirationSecond, refreshSecretKey, 100);
			JwtService jwtService = new JwtService(jwtConfig);

			Long memberId = 1L;
			Member member = MemberBuilder.build(memberId);
			String token = jwtService.generateAccessToken(String.valueOf(member.getId()));

			//given
			assertThatThrownBy(() -> jwtService.isAccessTokenValid(token)).isInstanceOf(JwtException.class);
		}
	}

}