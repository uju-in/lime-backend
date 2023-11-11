package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.member.application.dto.response.LoginMemberServiceResponse;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.repository.MemberRepository;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberManager {

	private final SecurityManager securityManager;
	private final MemberRepository memberRepository;

	public LoginMemberServiceResponse login(
		final String rawPassword,
		final Member member
	) {
		final Long memberId = member.getId();
		final String nickname = member.getNickname();

		securityManager.authenticate(memberId, rawPassword);
		final String jwtToken = securityManager.generateToken(member);

		return new LoginMemberServiceResponse(memberId, nickname, jwtToken);
	}

	public void checkNicknameDuplication(final String nickname) {
		if (memberRepository.existsByNickname(nickname)) {
			throw new BusinessException(ErrorCode.MEMBER_NICKNAME_DUPLICATE);
		}
	}

	public void checkEmailDuplication(final String email) {
		if (memberRepository.existsByLoginInfoEmail(email)) {
			throw new BusinessException(ErrorCode.MEMBER_EMAIL_EXIST);
		}
	}
}
