package com.programmers.bucketback.domains.bucket.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.bucket.api.dto.response.GetBucketResponse;
import com.programmers.bucketback.domains.bucket.domain.Bucket;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketService {

	private final BucketAppender bucketAppender;
	private final BucketModifier bucketModifier;
	private final BucketRemover bucketRemover;
	private final BucketReader bucketReader;

	/** 버킷 생성 */
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

	/** 버킷 상세 조회 */
	public GetBucketResponse getBucket(final Long bucketId) {
		Bucket bucket = bucketReader.read(bucketId);

		return new GetBucketResponse(BucketContent.from(bucket), BucketItemContent.from(bucket));
	}
}
