package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.member.application.dto.response.LoginMemberServiceResponse;
import com.programmers.bucketback.domains.member.domain.LoginInfo;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final SecurityManager securityManager;
	private final MemberAppender memberAppender;
	private final MemberReader memberReader;
	private final MemberRemover memberRemover;
	private final MemberModifier memberModifier;
	private final MemberChecker memberChecker;
	private final EmailSender emailSender;

	public void signup(
		final LoginInfo loginInfo,
		final String nickname
	) {
		memberAppender.append(loginInfo, nickname);
	}

	public LoginMemberServiceResponse login(final LoginInfo loginInfo) {
		final Member member = memberReader.read(loginInfo.getEmail());
		final Long memberId = member.getId();
		final String nickname = member.getNickname();
		final String rawPassword = loginInfo.getPassword();

		if (member.isDeleted()) {
			throw new BusinessException(ErrorCode.MEMBER_DELETED);
		}

		securityManager.authenticate(memberId, rawPassword);
		final String jwtToken = securityManager.generateToken(member);

		return new LoginMemberServiceResponse(memberId, nickname, jwtToken);
	}

	public void deleteMember() {
		memberRemover.remove();
	}

	public void updateProfile(
		final String nickname,
		final String introduction
	) {
		memberModifier.modify(nickname, introduction);
	}

	public void updatePassword(final String password) {
		memberModifier.modify(password);
	}

	public boolean checkNickname(final String nickname) {
		return memberChecker.checkNicknameDuplication(nickname);
	}

	public String checkEmail(final String email) {
		memberChecker.checkEmailDuplication(email);

		try {
			return emailSender.send(email);
		} catch (final MessagingException e) {
			throw new BusinessException(ErrorCode.MAIL_SEND_FAIL);
		}
	}
}
