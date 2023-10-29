package com.programmers.bucketback.domains.bucket.application;

import java.util.List;

import com.programmers.bucketback.domains.common.Hobby;

import lombok.Builder;

@Builder
public record BucketContent(

	Hobby hobby,
	String name,
	Integer budget,
	List<Long> itemIds
) {

}
