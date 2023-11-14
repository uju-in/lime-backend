package com.programmers.bucketback.domains.member.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.member.application.InventoryProfile;
import com.programmers.bucketback.domains.member.application.vo.BucketProfile;
import com.programmers.bucketback.domains.member.application.vo.MemberProfile;
import com.programmers.bucketback.domains.member.application.vo.MyPage;

public record MemberGetMyPageResponse(
	MemberProfile memberProfile,
	List<BucketProfile> bucketProfiles,
	List<InventoryProfile> inventoryProfiles
) {
	public static MemberGetMyPageResponse from(final MyPage myPage) {
		return new MemberGetMyPageResponse(
			myPage.memberProfile(),
			myPage.bucketProfiles(),
			myPage.inventoryProfiles()
		);
	}
}
