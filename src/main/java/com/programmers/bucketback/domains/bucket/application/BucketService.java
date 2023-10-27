package com.programmers.bucketback.domains.bucket.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketService {

	private final BucketAppender bucketAppender;

	//멤버id 정보 추가받아야함
	public void createBucket(
		BucketContent content
	) {
		bucketAppender.append(content);
	}
}
