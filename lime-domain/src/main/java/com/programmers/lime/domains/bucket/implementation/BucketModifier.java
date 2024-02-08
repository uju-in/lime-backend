package com.programmers.lime.domains.bucket.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.domains.bucket.domain.Bucket;
import com.programmers.lime.domains.bucket.domain.BucketInfo;
import com.programmers.lime.domains.bucket.domain.BucketItem;
import com.programmers.lime.domains.bucket.repository.BucketItemRepository;
import com.programmers.lime.domains.bucket.repository.BucketRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketModifier {

	private final BucketAppender bucketAppender;
	private final BucketRemover bucketRemover;
	private final BucketReader bucketReader;
	private final BucketItemRepository bucketItemRepository;
	private final BucketRepository bucketRepository;

	/** 버킷 수정 */
	@Transactional
	public void modify(
		final Long bucketId,
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		Bucket bucket = bucketReader.read(bucketId);
		bucket.modifyBucket(bucketInfo);

		bucketRemover.removeBucketItems(bucketId);
		List<BucketItem> bucketItems = bucketAppender.createBucketItems(registry, bucketId);

		bucketRepository.save(bucket);
		bucketItemRepository.saveAll(bucketItems);
	}
}
