package com.programmers.lime.domains.member.model;

import java.util.List;

import com.programmers.lime.domains.bucket.model.BucketProfile;
import com.programmers.lime.domains.inventory.model.InventoryProfile;

public record MyPage(
	MemberProfile memberProfile,
	List<BucketProfile> bucketProfiles,
	List<InventoryProfile> inventoryProfiles
) {
}
