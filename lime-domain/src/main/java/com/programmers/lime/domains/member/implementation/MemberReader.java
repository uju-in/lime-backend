package com.programmers.lime.domains.member.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.bucket.implementation.BucketReader;
import com.programmers.lime.domains.bucket.model.BucketProfile;
import com.programmers.lime.domains.friendships.implementation.FriendshipCounter;
import com.programmers.lime.domains.inventory.implementation.InventoryReader;
import com.programmers.lime.domains.inventory.model.InventoryProfile;
import com.programmers.lime.domains.member.domain.vo.SocialType;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.model.MemberProfile;
import com.programmers.lime.domains.member.model.MyPage;
import com.programmers.lime.domains.member.repository.MemberRepository;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReader {

	private final MemberRepository memberRepository;
	private final BucketReader bucketReader;
	private final InventoryReader inventoryReader;
	private final FriendshipCounter friendshipCounter;

	public Member read(final Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public Member readByEmail(final String email) {
		return memberRepository.findBySocialInfoEmail(email)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_LOGIN_FAIL));
	}

	public Member readByNickname(final String nickname) {
		return memberRepository.findByNicknameNickname(nickname)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public MyPage readMyPage(final Member member) {
		final long followerCount = friendshipCounter.countFollower(member.getId());
		final long followingCount = friendshipCounter.countFollowing(member.getId());
		final MemberProfile memberProfile = MemberProfile.from(member, followerCount, followingCount);

		final List<BucketProfile> bucketProfiles = bucketReader.readBucketProfile(member.getId());

		final List<InventoryProfile> inventoryProfiles = inventoryReader.readInventoryProfile(member.getId());

		return new MyPage(memberProfile, bucketProfiles, inventoryProfiles);
	}

	public Optional<Member> readBySocialIdAndSocialType(String socialId, SocialType socialType) {
		return memberRepository.findBySocialInfoSocialIdAndSocialInfoSocialType(socialId, socialType);
	}
}
