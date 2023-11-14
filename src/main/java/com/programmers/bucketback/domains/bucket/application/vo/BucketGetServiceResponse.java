package com.programmers.bucketback.domains.bucket.application.vo;

import java.util.List;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.item.application.vo.ItemInfo;

public record BucketGetServiceResponse(
	Bucket bucket,
	List<ItemInfo> itemInfos
) {
}
