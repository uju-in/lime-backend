package com.programmers.bucketback.domains.bucket.model;

import java.util.List;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.item.model.ItemInfo;

public record BucketGetServiceResponse(
	Bucket bucket,
	List<ItemInfo> itemInfos
) {
}
