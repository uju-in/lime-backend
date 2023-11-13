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

	private final MemberAppender memberAppender;
	private final MemberReader memberReader;
	private final MemberRemover memberRemover;
	private final MemberModifier memberModifier;
	private final MemberManager memberManager;
	private final EmailSender emailSender;

	public void signup(
		final LoginInfo loginInfo,
		final String nickname
	) {
		memberAppender.append(loginInfo, nickname);
	}

	public LoginMemberServiceResponse login(final LoginInfo loginInfo) {
		final String email = loginInfo.getEmail();
		final String rawPassword = loginInfo.getPassword();
		final Member member = memberReader.readByEmail(email);

		if (member.isDeleted()) {
			throw new BusinessException(ErrorCode.MEMBER_DELETED);
		}

		return memberManager.login(rawPassword, member);
	}

	public void deleteMember() {
		memberRemover.remove();
	}

	public void updateProfile(
		final String nickname,
		final String introduction
	) {
		memberModifier.modifyProfile(nickname, introduction);
	}

	public void updatePassword(final String password) {
		memberModifier.modifyPassword(password);
	}

	public boolean checkNickname(final String nickname) {
		return memberManager.checkNicknameDuplication(nickname);
	}

	public String checkEmail(final String email) {
		memberManager.checkEmailDuplication(email);

		try {
			return emailSender.send(email);
		} catch (final MessagingException e) {
			throw new BusinessException(ErrorCode.MAIL_SEND_FAIL);
		}
	}
}
