package com.programmers.bucketback.global.config.security.jwt;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.vo.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

class JwtServiceTest {

	private String accessSecretKey = "testsecretkeydaslkjslsdnmflsdkgjsldgkjlweptojkgn";
	private String refreshSecretKey = "testsecretkeydaslkjslsdnmflsdkgjsldgkjlweptojkgn";
	private JwtConfig jwtConfig = new JwtConfig(accessSecretKey, 100, refreshSecretKey, 100);
	private JwtService jwtService = new JwtService(jwtConfig);

	private static Member buildMember() {
		Member member = Member.builder()
			.email("email@email.com")
			.password("password!123")
			.nickname("nickname")
			.role(Role.USER)
			.build();
		return member;
	}

	@Test
	@DisplayName("access token 생성에 성공한다.")
	void successAccessTokenCreate() {
		//given
		Member member = buildMember();
		ReflectionTestUtils.setField(member, "id", 1L);

		//when
		String token = jwtService.generateAccessToken(String.valueOf(member.getId()));
		String username = jwtService.extractUsername(token);

		byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.accessSecretKey());
		SecretKey decodedSecretKey = Keys.hmacShaKeyFor(keyBytes);
		Claims claims = Jwts.parserBuilder().setSigningKey(decodedSecretKey).build().parseClaimsJws(token).getBody();

		//then
		assertThat(claims.getExpiration()).isAfter(new Date());
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

			Member member = buildMember();
			ReflectionTestUtils.setField(member, "id", 1L);
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

			Member member = buildMember();
			ReflectionTestUtils.setField(member, "id", 1L);
			String token = jwtService.generateAccessToken(String.valueOf(member.getId()));

			//given
			assertThatThrownBy(() -> jwtService.isAccessTokenValid(token)).isInstanceOf(JwtException.class);
		}
	}

}