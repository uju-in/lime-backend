package com.programmers.lime.domains.member.implementation;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.MemberBuilder;
import com.programmers.lime.domains.member.domain.vo.MemberStatus;

@ExtendWith(MockitoExtension.class)
class MemberRemoverTest {

	@InjectMocks
	private MemberRemover memberRemover;

	@Test
	@DisplayName("회원을 삭제한다.")
	void removeTest() {
		// given
		final Member member = MemberBuilder.build(1L);

		// when
		memberRemover.remove(member);

		// then
		assertThat(member.getStatus()).isEqualTo(MemberStatus.DELETED);
	}

	@Test
	@DisplayName("프로필 이미지를 삭제한다.")
	void removeProfileImageTest() {
		// given
		final Member member = MemberBuilder.build(1L);

		// when
		memberRemover.removeProfileImage(member);

		// then
		assertThat(member.getProfileImage()).isNull();
	}
}
