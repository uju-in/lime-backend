package com.programmers.bucketback.domains.member.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberModifier {

	private final MemberReader memberReader;
	private final MemberChecker memberChecker;

	@Transactional
	public void modifyProfile(
		final Long memberId,
		final String nickname,
		final String introduction
	) {
		final Member member = memberReader.read(memberId);

		if (!nickname.equals(member.getNickname())) {
			memberChecker.checkNicknameDuplication(nickname);
		}

		member.updateProfile(nickname, introduction);
	}

	@Transactional
	public void modifyPassword(
		final Long memberId,
		final String encodedPassword
	) {
		final Member member = memberReader.read(memberId);
		member.updatePassword(encodedPassword);
	}

	@Transactional
	public void modifyProfileImage(
		final Long memberId,
		final String profileImage
	) {
		final Member member = memberReader.read(memberId);
		member.updateProfileImage(profileImage);
	}
}
