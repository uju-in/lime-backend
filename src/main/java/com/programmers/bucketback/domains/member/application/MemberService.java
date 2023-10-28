package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.member.application.dto.request.SignupMemberServiceRequest;
import com.programmers.bucketback.domains.member.application.dto.response.LoginMemberServiceResponse;
import com.programmers.bucketback.domains.member.domain.LoginInfo;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final SecurityManager securityManager;
	private final MemberAppender memberAppender;
	private final MemberReader memberReader;

	@Transactional
	public void signup(final SignupMemberServiceRequest request) {
		memberAppender.append(request.email(), request.password(), request.nickname());
	}

	public LoginMemberServiceResponse login(final LoginInfo loginInfo) {
		final Member member = memberReader.read(loginInfo.getEmail());

		if (member.isDeleted()) {
			throw new BusinessException(ErrorCode.MEMBER_DELETED);
		}

		securityManager.authenticate(member.getId(), loginInfo.getPassword());

		final String jwtToken = securityManager.generateToken(member);

		return new LoginMemberServiceResponse(member.getNickname(), jwtToken);
	}

	@Transactional
	public void deleteMember() {
		final Long memberId = MemberUtils.getCurrentMemberId();
		final Member member = memberReader.read(memberId);

		member.delete();
	}
}
