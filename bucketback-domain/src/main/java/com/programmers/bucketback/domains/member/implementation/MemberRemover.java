package com.programmers.bucketback.domains.member.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberRemover {

	@Transactional
	public void remove(final Member member) {
		member.delete();
	}

	@Transactional
	public void removeProfileImage(final Member member) {
		member.updateProfileImage(null);
	}
}
