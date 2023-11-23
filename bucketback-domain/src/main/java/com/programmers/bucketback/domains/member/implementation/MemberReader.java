package com.programmers.bucketback.domains.member.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.implementation.BucketReader;
import com.programmers.bucketback.domains.bucket.model.BucketProfile;
import com.programmers.bucketback.domains.inventory.implementation.InventoryReader;
import com.programmers.bucketback.domains.inventory.model.InventoryProfile;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.model.MemberProfile;
import com.programmers.bucketback.domains.member.model.MyPage;
import com.programmers.bucketback.domains.member.repository.MemberRepository;
import com.programmers.bucketback.error.EntityNotFoundException;
import com.programmers.bucketback.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReader {

	private final MemberRepository memberRepository;
	private final BucketReader bucketReader;
	private final InventoryReader inventoryReader;

	public Member read(final Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public Member readByEmail(final String email) {
		return memberRepository.findByLoginInfoEmail(email)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_LOGIN_FAIL));
	}

	public Member readByNickname(final String nickname) {
		return memberRepository.findByNicknameNickname(nickname)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public MyPage readMyPage(final Member member) {
		MemberProfile memberProfile = MemberProfile.from(member);

		List<BucketProfile> bucketProfiles = bucketReader.readBucketProfile(member.getId());

		List<InventoryProfile> inventoryProfiles = inventoryReader.readInventoryProfile(member.getId());

		return new MyPage(memberProfile, bucketProfiles, inventoryProfiles);
	}
}
