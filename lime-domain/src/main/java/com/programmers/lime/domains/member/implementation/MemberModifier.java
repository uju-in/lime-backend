package com.programmers.lime.domains.member.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.vo.Introduction;
import com.programmers.lime.domains.member.domain.vo.Role;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberModifier {

	public static final String IMAGE_BASE_PATH = "https://team-02-bucket.s3.ap-northeast-2.amazonaws.com/";

	private final MemberChecker memberChecker;

	@Transactional
	public void modifyProfile(
		final Member member,
		final String nickname,
		final Introduction introduction
	) {
		if (!nickname.equals(member.getNickname())) {
			memberChecker.checkNicknameDuplication(nickname);
		}

		member.updateProfile(nickname, introduction);
	}

	@Transactional
	public void modifyRole(
		final Member member,
		final Role role
	) {
		member.updateRole(role);
	}

	@Transactional
	public void modifyProfileImage(
		final Member member,
		final String directory,
		final String profileImage
	) {
		final String imagePath = IMAGE_BASE_PATH + directory + "/" + profileImage;

		member.updateProfileImage(imagePath);
	}
}
