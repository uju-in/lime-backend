package com.programmers.bucketback.domains.member.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.domains.bucket.implementation.BucketReader;
import com.programmers.bucketback.domains.inventory.implementation.InventoryReader;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.MemberBuilder;
import com.programmers.bucketback.domains.member.repository.MemberRepository;
import com.programmers.bucketback.error.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class MemberReaderTest {

	@InjectMocks
	private MemberReader memberReader;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private BucketReader bucketReader;

	@Mock
	private InventoryReader inventoryReader;

	@Nested
	@DisplayName("memberId로 회원 조회 테스트")
	class ReadTest {
		@Test
		@DisplayName("주어진 memberId를 가진 회원을 반환한다.")
		void successTest() {
			// given
			final Long memberId = 1L;
			final Member member = MemberBuilder.build(memberId);

			given(memberRepository.findById(anyLong()))
				.willReturn(Optional.ofNullable(member));

			// when
			final Member result = memberReader.read(memberId);

			// then
			assertThat(result).isEqualTo(member);
		}

		@Test
		@DisplayName("주어진 memberId를 가진 회원이 없으면 예외가 발생한다.")
		void occurExceptionIfNotExistTest() {
			// given
			final Long memberId = 1L;

			given(memberRepository.findById(anyLong()))
				.willReturn(Optional.empty());

			// when & then
			assertThatThrownBy(
				() -> memberReader.read(memberId)
			)
				.isInstanceOf(EntityNotFoundException.class);
		}
	}
}
