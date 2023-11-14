package com.programmers.bucketback.domains.member.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.bucket.application.BucketReader;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.inventory.application.InventoryReader;
import com.programmers.bucketback.domains.inventory.repository.InventoryRepository;
import com.programmers.bucketback.domains.member.application.vo.BucketProfile;
import com.programmers.bucketback.domains.member.application.vo.MemberProfile;
import com.programmers.bucketback.domains.member.application.vo.MyPage;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.repository.MemberRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberReader {

	private final MemberRepository memberRepository;
	private final InventoryRepository inventoryRepository;
	private final BucketReader bucketReader;
	private final InventoryReader inventoryReader;

	public Member read(final String email) {
		return memberRepository.findByLoginInfoEmail(email)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_LOGIN_FAIL));
	}

	public Member read() {
		final Long memberId = MemberUtils.getCurrentMemberId();

		return memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public Member readByNickname(final String nickname) {
		return memberRepository.findByNickname(nickname)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public MyPage readMyPage(final String nickname) {
		Member member = readByNickname(nickname);
		MemberProfile memberProfile = MemberProfile.from(member);

		List<BucketProfile> bucketProfiles = bucketReader.readBucketProfile(member.getId());

		List<InventoryProfile> inventoryProfiles = inventoryReader.readInventoryProfile(member.getId());

		return new MyPage(memberProfile, bucketProfiles, inventoryProfiles);
	}

}
