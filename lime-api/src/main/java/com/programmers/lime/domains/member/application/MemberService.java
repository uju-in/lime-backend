package com.programmers.lime.domains.member.application;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.programmers.lime.domains.member.application.dto.response.MemberCheckJwtServiceResponse;
import com.programmers.lime.domains.member.application.dto.response.MemberLoginServiceResponse;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.vo.Introduction;
import com.programmers.lime.domains.member.domain.vo.LoginInfo;
import com.programmers.lime.domains.member.implementation.MemberAppender;
import com.programmers.lime.domains.member.implementation.MemberChecker;
import com.programmers.lime.domains.member.implementation.MemberModifier;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.domains.member.implementation.MemberRemover;
import com.programmers.lime.domains.member.model.MyPage;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.util.MemberUtils;
import com.programmers.lime.mail.EmailSender;
import com.programmers.lime.s3.S3Manager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	public static final String DIRECTORY = "bucketback-static";
	public static final String RESIZED_DIRECTORY = "resized";

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

	public String extendLogin(
		final String refreshToken,
		final String authorizationHeader
	) {
		return memberSecurityManager.reissueAccessToken(refreshToken, authorizationHeader);
	}

	public void logout(final String refreshToken) {
		memberSecurityManager.removeRefreshToken(refreshToken);
	}

	public void deleteMember(final String refreshToken) {
		final Member member = memberUtils.getCurrentMember();
		memberRemover.remove(member);
		memberSecurityManager.removeRefreshToken(refreshToken);
	}

	public void updateProfile(
		final String nickname,
		final Introduction introduction
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
		Member member = memberReader.readByNickname(nickname);
		if (member.isDeleted()) {
			throw new BusinessException(ErrorCode.MEMBER_DELETED);
		}

		return memberReader.readMyPage(member);
	}

	public void updateProfileImage(final MultipartFile multipartFile) throws IOException {
		final Member member = memberUtils.getCurrentMember();

		if (member.getProfileImage() != null) {
			final int lastSlashIndex = member.getProfileImage().lastIndexOf("/");
			final String originProfileImage = member.getProfileImage().substring(lastSlashIndex + 1);
			s3Manager.deleteFile(DIRECTORY, originProfileImage);
			s3Manager.deleteFile(RESIZED_DIRECTORY, originProfileImage);
		}

		if (multipartFile == null) {
			memberRemover.removeProfileImage(member);
			return;
		}

		final String fileType = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
		final String profileImage = UUID.randomUUID() + "." + fileType;

		s3Manager.uploadFile(multipartFile, DIRECTORY, profileImage);
		memberModifier.modifyProfileImage(member, RESIZED_DIRECTORY, profileImage);
	}
}
