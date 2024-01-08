package com.programmers.lime.domains.member.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.bucket.model.BucketProfile;
import com.programmers.lime.domains.inventory.model.InventoryProfile;
import com.programmers.lime.domains.member.model.MemberProfile;
import com.programmers.lime.domains.member.model.MyPage;

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
