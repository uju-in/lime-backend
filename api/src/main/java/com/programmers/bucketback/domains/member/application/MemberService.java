package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.member.domain.vo.LoginInfo;
import com.programmers.bucketback.domains.member.implementation.MemberAppender;
import com.programmers.bucketback.domains.member.implementation.MemberManager;
import com.programmers.bucketback.domains.member.implementation.MemberModifier;
import com.programmers.bucketback.domains.member.implementation.MemberReader;
import com.programmers.bucketback.domains.member.implementation.MemberRemover;
import com.programmers.bucketback.domains.member.model.MyPage;
import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;
import com.programmers.bucketback.global.util.MemberUtils;
import com.programmers.bucketback.mail.EmailSender;

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
		final String email = loginInfo.getEmail();
		memberManager.checkEmailDuplication(email);
		memberManager.checkNicknameDuplication(nickname);

		// memberAppender.append(loginInfo, nickname);
	}

	// public MemberLoginServiceResponse login(final LoginInfo loginInfo) {
	// 	final String email = loginInfo.getEmail();
	// 	final String rawPassword = loginInfo.getPassword();
	// 	final Member member = memberReader.readByEmail(email);
	//
	// 	if (member.isDeleted()) {
	// 		throw new BusinessException(ErrorCode.MEMBER_DELETED);
	// 	}
	//
	// 	return memberManager.login(rawPassword, member);
	// }

	public void deleteMember() {
		final Long memberId = MemberUtils.getCurrentMemberId();

		memberRemover.remove(memberId);
	}

	public void updateProfile(
		final String nickname,
		final String introduction
	) {
		final Long memberId = MemberUtils.getCurrentMemberId();
		memberManager.checkNicknameDuplication(nickname);

		memberModifier.modifyProfile(memberId, nickname, introduction);
	}

	public void updatePassword(final String password) {
		final Long memberId = MemberUtils.getCurrentMemberId();

		// memberModifier.modifyPassword(memberId, password);
	}

	public void checkNickname(final String nickname) {
		memberManager.checkNicknameDuplication(nickname);
	}

	public String checkEmail(final String email) {
		memberManager.checkEmailDuplication(email);

		try {
			return emailSender.send(email);
		} catch (final MessagingException e) {
			throw new BusinessException(ErrorCode.MAIL_SEND_FAIL);
		}
	}

	public MyPage getMyPage(final String nickname) {
		return memberReader.readMyPage(nickname);
	}
}
