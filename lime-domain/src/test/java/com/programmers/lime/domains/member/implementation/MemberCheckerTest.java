package com.programmers.lime.domains.member.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.domains.member.repository.MemberRepository;
import com.programmers.lime.error.BusinessException;

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

			// when & then
			assertDoesNotThrow(() -> memberChecker.checkNicknameDuplication(nickname));
		}

		@Test
		@DisplayName("닉네임을 가진 회원이 있으면 예외가 발생한다.")
		void occurExceptionIfDuplicateTest() {
			// given
			final String nickname = "best_park";

			given(memberRepository.existsByNicknameNickname(anyString()))
				.willReturn(true);

			// when & then
			assertThatThrownBy(
				() -> memberChecker.checkNicknameDuplication(nickname)
			)
				.isInstanceOf(BusinessException.class);
		}
	}

	@Nested
	@DisplayName("이메일 중복 확인 테스트")
	class CheckEmailDuplicationTest {
		@Test
		@DisplayName("이메일을 가진 회원이 없으면 아무일도 일어나지 않는다.")
		void doNotDuplicateTest() {
			// given
			final String email = "test@test.com";

			given(memberRepository.existsByLoginInfoEmail(anyString()))
				.willReturn(false);

			// when & then
			assertDoesNotThrow(() -> memberChecker.checkEmailDuplication(email));
		}

		@Test
		@DisplayName("이메일을 가진 회원이 있으면 예외가 발생한다.")
		void occurExceptionIfDuplicateTest() {
			// given
			final String email = "test@test.com";

			given(memberRepository.existsByLoginInfoEmail(anyString()))
				.willReturn(true);

			// when & then
			assertThatThrownBy(
				() -> memberChecker.checkEmailDuplication(email)
			)
				.isInstanceOf(BusinessException.class);
		}
	}
}
