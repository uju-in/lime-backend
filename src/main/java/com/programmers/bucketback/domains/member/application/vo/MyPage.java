package com.programmers.bucketback.domains.member.application.vo;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketProfile;
import com.programmers.bucketback.domains.inventory.application.vo.InventoryProfile;

public record MyPage(
	MemberProfile memberProfile,
	List<BucketProfile> bucketProfiles,
	List<InventoryProfile> inventoryProfiles
) {
}
