package com.programmers.bucketback.domains.bucket.api.dto.response;

import com.programmers.bucketback.domains.bucket.application.vo.BucketContent;
import com.programmers.bucketback.domains.bucket.application.vo.BucketItemContent;

public record GetBucketResponse(
	BucketContent bucketContent,
	BucketItemContent bucketItemContent
) {
}
