package com.programmers.lime.domains.member.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.domains.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberCheckerTest {

	@InjectMocks
	private MemberChecker memberChecker;

	@Mock
	private MemberRepository memberRepository;

	@Nested
	@DisplayName("닉네임 중복 확인 테스트")
	class CheckNicknameDuplicationTest {
		@Test
		@DisplayName("닉네임을 가진 회원이 없으면 아무일도 일어나지 않는다.")
		void doNotDuplicateTest() {
			// given
			final String nickname = "best_park";

			given(memberRepository.existsByNicknameNickname(anyString()))
				.willReturn(false);

			// when
			final boolean result = memberChecker.checkNicknameDuplication(nickname);

			// then
			assertThat(result).isFalse();
		}

		@Test
		@DisplayName("닉네임을 가진 회원이 있으면 예외가 발생한다.")
		void occurExceptionIfDuplicateTest() {
			// given
			final String nickname = "best_park";

			given(memberRepository.existsByNicknameNickname(anyString()))
				.willReturn(true);

			// when
			final boolean result = memberChecker.checkNicknameDuplication(nickname);

			// then
			assertThat(result).isTrue();
		}
	}
}
