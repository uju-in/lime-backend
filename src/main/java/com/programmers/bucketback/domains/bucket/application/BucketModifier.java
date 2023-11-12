package com.programmers.bucketback.domains.bucket.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.application.vo.ItemIdRegistry;
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
	private final BucketRepository bucketRepository;

	/** 버킷 수정 */
	@Transactional
	public void modify(
		final Bucket bucket,
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		bucket.removeBucketItems();
		bucketRemover.removeBucketItems(bucket.getId());

		List<BucketItem> bucketItems = bucketAppender.createBucketItems(registry);
		bucket.modifyBucket(bucketInfo, bucket.getMemberId());
		bucketItems.forEach(bucket::addBucketItem);

		bucketRepository.save(bucket);
	}
}
