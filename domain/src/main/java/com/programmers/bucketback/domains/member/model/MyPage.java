package com.programmers.bucketback.domains.member.model;

import java.util.List;

import com.programmers.bucketback.domains.bucket.model.BucketProfile;
import com.programmers.bucketback.domains.inventory.model.InventoryProfile;

public record MyPage(
	MemberProfile memberProfile,
	List<BucketProfile> bucketProfiles,
	List<InventoryProfile> inventoryProfiles
) {
}
