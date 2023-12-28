package com.programmers.bucketback.global.config.security.jwt;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.vo.Role;
import com.programmers.bucketback.global.config.security.MemberSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

class JwtServiceTest {

	private String secretKey = "testsecretkeydaslkjslsdnmflsdkgjsldgkjlweptojkgn";
	private JwtConfig jwtConfig = new JwtConfig(secretKey, 100);
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
		MemberSecurity memberSecurity = new MemberSecurity(member);

		//when
		String token = jwtService.generateToken(memberSecurity);
		String username = jwtService.extractUsername(token);

		byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.secretKey());
		SecretKey decodedSecretKey = Keys.hmacShaKeyFor(keyBytes);
		Claims claims = Jwts.parserBuilder().setSigningKey(decodedSecretKey).build().parseClaimsJws(token).getBody();

		//then
		assertThat(claims.getExpiration()).isAfter(new Date());
		assertThat(username).isEqualTo(String.valueOf(member.getId()));
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 10, 1000})
	@DisplayName("유효한 토큰 유효성 검사에 성공한다.")
	void successValidTokenEvaluation(int expirationSecond) {
		//given
		String secretKey = "testsecretkeydaslkjslsdnmflsdkgjsldgkjlweptojkgn";
		JwtConfig jwtConfig = new JwtConfig(secretKey, expirationSecond);
		JwtService jwtService = new JwtService(jwtConfig);

		Member member = buildMember();
		ReflectionTestUtils.setField(member, "id", 1L);
		MemberSecurity memberSecurity = new MemberSecurity(member);
		String token = jwtService.generateToken(memberSecurity);

		//given
		boolean result = jwtService.isTokenValid(token, memberSecurity);
		assertThat(result).isEqualTo(true);
	}

	@ParameterizedTest
	@ValueSource(ints = {0})
	@DisplayName("유효하지 않은 토큰 유효성 검사에 성공한다.")
	void successInValidTokenEvaluation() {
		//given
		String secretKey = "testsecretkeydaslkjslsdnmflsdkgjsldgkjlweptojkgn";
		JwtConfig jwtConfig = new JwtConfig(secretKey, 0);
		JwtService jwtService = new JwtService(jwtConfig);

		Member member = buildMember();
		ReflectionTestUtils.setField(member, "id", 1L);
		MemberSecurity memberSecurity = new MemberSecurity(member);
		String token = jwtService.generateToken(memberSecurity);

		//given
		boolean result = jwtService.isTokenValid(token, memberSecurity);
		assertThat(result).isEqualTo(false);
	}

}