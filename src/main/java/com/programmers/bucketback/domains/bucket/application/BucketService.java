package com.programmers.bucketback.domains.bucket.application;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketService {

	private final BucketAppender bucketAppender;
	private final BucketModifier bucketModifier;
	private final BucketRemover bucketRemover;

	/** 버킷 생성 */
	//멤버id 정보 추가받아야함
	public void createBucket(final BucketContent content) {
		bucketAppender.append(content);
	}

	/** 버킷 수정 */
	public void modifyBucket(
		final Long bucketId,
		final BucketContent content
	) {
		bucketModifier.modify(bucketId, content);

	}

	/** 버킷 삭제 */
	public void deleteBucket(final Long bucketId) {
		bucketRemover.remove(bucketId);
	}
}
