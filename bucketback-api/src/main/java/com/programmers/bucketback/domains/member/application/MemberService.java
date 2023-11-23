package com.programmers.bucketback.domains.member.application;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.programmers.bucketback.domains.member.application.dto.response.MemberCheckJwtServiceResponse;
import com.programmers.bucketback.domains.member.application.dto.response.MemberLoginServiceResponse;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.vo.LoginInfo;
import com.programmers.bucketback.domains.member.implementation.MemberAppender;
import com.programmers.bucketback.domains.member.implementation.MemberChecker;
import com.programmers.bucketback.domains.member.implementation.MemberModifier;
import com.programmers.bucketback.domains.member.implementation.MemberReader;
import com.programmers.bucketback.domains.member.implementation.MemberRemover;
import com.programmers.bucketback.domains.member.model.MyPage;
import com.programmers.bucketback.error.BusinessException;
import com.programmers.bucketback.error.ErrorCode;
import com.programmers.bucketback.global.util.MemberUtils;
import com.programmers.bucketback.mail.EmailSender;
import com.programmers.bucketback.s3.S3Manager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	public static final String DIRECTORY = "bucketback-static";
	public static final String BASE_EXTENSION = ".png";

	private final MemberAppender memberAppender;
	private final MemberReader memberReader;
	private final MemberRemover memberRemover;
	private final MemberModifier memberModifier;
	private final MemberSecurityManager memberSecurityManager;
	private final MemberChecker memberChecker;
	private final EmailSender emailSender;
	private final S3Manager s3Manager;
	private final MemberUtils memberUtils;

	public MemberCheckJwtServiceResponse checkJwtToken() {
		return memberSecurityManager.checkJwtToken();
	}

	public void signup(
		final LoginInfo loginInfo,
		final String nickname
	) {
		memberChecker.checkEmailDuplication(loginInfo.getEmail());
		memberChecker.checkNicknameDuplication(nickname);
		final String encodedPassword = memberSecurityManager.encodePassword(loginInfo.getPassword());

		memberAppender.append(loginInfo.getEmail(), encodedPassword, nickname);
	}

	public MemberLoginServiceResponse login(final LoginInfo loginInfo) {
		final String email = loginInfo.getEmail();
		final String rawPassword = loginInfo.getPassword();
		final Member member = memberReader.readByEmail(email);

		if (member.isDeleted()) {
			throw new BusinessException(ErrorCode.MEMBER_DELETED);
		}

		return memberSecurityManager.login(rawPassword, member);
	}

	public void deleteMember() {
		final Member member = memberUtils.getCurrentMember();

		memberRemover.remove(member);
	}

	public void updateProfile(
		final String nickname,
		final String introduction
	) {
		final Member member = memberUtils.getCurrentMember();

		memberModifier.modifyProfile(member, nickname, introduction);
	}

	public void updatePassword(final String password) {
		final Member member = memberUtils.getCurrentMember();
		final String encodedPassword = memberSecurityManager.encodePassword(password);

		memberModifier.modifyPassword(member, encodedPassword);
	}

	public void checkNickname(final String nickname) {
		memberChecker.checkNicknameDuplication(nickname);
	}

	public String checkEmail(final String email) {
		memberChecker.checkEmailDuplication(email);

		return emailSender.send(email);
	}

	public MyPage getMyPage(final String nickname) {
		return memberReader.readMyPage(nickname);
	}

	public void updateProfileImage(final MultipartFile multipartFile) throws IOException {
		final Member member = memberUtils.getCurrentMember();
		final String profileImage = member.getId() + BASE_EXTENSION;

		s3Manager.deleteFile(profileImage, DIRECTORY);

		if (multipartFile == null) {
			memberRemover.removeProfileImage(member);
			return;
		}

		s3Manager.uploadFile(multipartFile, DIRECTORY, profileImage);
		memberModifier.modifyProfileImage(member, DIRECTORY, profileImage);
	}
}
