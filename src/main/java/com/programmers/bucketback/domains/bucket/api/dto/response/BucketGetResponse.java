package com.programmers.bucketback.domains.bucket.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketContent;
import com.programmers.bucketback.domains.item.application.vo.ItemInfo;

public record BucketGetResponse(
	BucketContent bucketContent,
	List<ItemInfo> itemInfos
) {
}
