package com.programmers.bucketback.domains.bucket.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.application.vo.BucketContent;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
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
		final BucketContent content
	) {
		bucket.removeBucketItems();
		bucketRemover.removeBucketItems(bucket.getId());

		List<BucketItem> bucketItems = bucketAppender.createBucketItems(content.bucketItemIds());
		bucket.modifyBucket(
			content.hobby(),
			content.bucketName(),
			content.bucketBudget()
		);
		bucketItems.forEach(bucket::addBucketItem);

		bucketRepository.save(bucket);
	}
}
