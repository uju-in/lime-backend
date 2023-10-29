package com.programmers.bucketback.domains.bucket.api.dto.response;

import com.programmers.bucketback.domains.bucket.application.BucketContent;
import com.programmers.bucketback.domains.bucket.application.BucketItemContent;

public record GetBucketResponse(
	BucketContent bucketContent,
	BucketItemContent bucketItemContent
) {
}
