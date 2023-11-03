package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.member.application.dto.response.LoginMemberServiceResponse;
import com.programmers.bucketback.domains.member.domain.LoginInfo;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final SecurityManager securityManager;
	private final MemberAppender memberAppender;
	private final MemberReader memberReader;
	private final MemberRemover memberRemover;
	private final MemberUpdater memberUpdater;
	private final MemberChecker memberChecker;
	private final EmailSender emailSender;

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
		memberRemover.remove();
	}

	@Transactional
	public void updateProfile(
		final String nickname,
		final String introduction
	) {
		memberUpdater.update(nickname, introduction);
	}

	@Transactional
	public void updatePassword(final String password) {
		memberUpdater.update(password);
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
