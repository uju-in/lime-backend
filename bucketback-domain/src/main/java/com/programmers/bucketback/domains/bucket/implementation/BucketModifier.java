package com.programmers.bucketback.domains.bucket.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.common.model.ItemIdRegistry;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
import com.programmers.bucketback.domains.bucket.domain.BucketItem;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketModifier {

	private final BucketAppender bucketAppender;
	private final BucketRemover bucketRemover;
	private final BucketReader bucketReader;
	private final BucketRepository bucketRepository;

	/** 버킷 수정 */
	@Transactional
	public void modify(
		final Long memberId,
		final Long bucketId,
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		Bucket bucket = bucketReader.read(bucketId, memberId);

		bucket.removeBucketItems();
		bucketRemover.removeBucketItems(bucket.getId());

		List<BucketItem> bucketItems = bucketAppender.createBucketItems(registry);
		bucket.modifyBucket(bucketInfo, bucket.getMemberId());
		bucketItems.forEach(bucket::addBucketItem);

		bucketRepository.save(bucket);
	}
}
