package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.member.application.dto.request.UpdateProfileMemberServiceRequest;
import com.programmers.bucketback.domains.member.application.dto.response.LoginMemberServiceResponse;
import com.programmers.bucketback.domains.member.domain.LoginInfo;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final SecurityManager securityManager;
	private final MemberAppender memberAppender;
	private final MemberReader memberReader;

	@Transactional
	public void signup(
		final LoginInfo loginInfo,
		final String nickname
	) {
		memberAppender.append(loginInfo, nickname);
	}

	public LoginMemberServiceResponse login(final LoginInfo loginInfo) {
		final Member member = memberReader.read(loginInfo.getEmail());

		if (member.isDeleted()) {
			throw new BusinessException(ErrorCode.MEMBER_DELETED);
		}

		securityManager.authenticate(member.getId(), loginInfo.getPassword());

		final String jwtToken = securityManager.generateToken(member);

		return new LoginMemberServiceResponse(member.getId(), member.getNickname(), jwtToken);
	}

	@Transactional
	public void deleteMember() {
		final Long memberId = MemberUtils.getCurrentMemberId();
		final Member member = memberReader.read(memberId);

		member.delete();
	}

	@Transactional
	public void updateProfile(final UpdateProfileMemberServiceRequest request) {
		final Long memberId = MemberUtils.getCurrentMemberId();
		final Member member = memberReader.read(memberId);

		member.updateProfile(request.nickname(), request.introduction());
	}
}
