package com.programmers.bucketback.domains.member.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.vo.Role;
import com.programmers.bucketback.domains.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberAppenderTest {

	@InjectMocks
	private MemberAppender memberAppender;

	@Mock
	private MemberRepository memberRepository;

	@Test
	@DisplayName("이메일, 암호화된 비밀번호, 닉네임로 회원을 생성 후 저장한다.")
	void appendTest() {
		// given
		final String email = "test@test.com";
		final String encodedPassword = "$2b$12$9136HMSjeym7mmJ5OgvCPusoDmCmAN5w1caMkXN8s7OuklKr755y6";
		final String nickname = "best_park";
		final Member member = Member.builder()
			.email(email)
			.password(encodedPassword)
			.nickname(nickname)
			.role(Role.USER)
			.build();

		given(memberRepository.save(any(Member.class)))
			.willReturn(member);

		// when
		final Member result = memberAppender.append(email, encodedPassword, nickname);

		// then
		assertThat(result).usingRecursiveComparison()
			.isEqualTo(member);
	}
}