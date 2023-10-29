package com.programmers.bucketback.domains.bucket.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketItem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketModifier {

	private final BucketReader bucketReader;
	private final BucketAppender bucketAppender;

	/** 버킷 수정 */
	@Transactional
	public void modify(
		final Long bucketId,
		final BucketContent content
	) {
		List<BucketItem> bucketItems = bucketAppender.createBucketItems(content.itemIds());
		Bucket bucket = bucketReader.read(bucketId);

		//memberId 반영후 수정 예
		// bucket.modifyBucket(
		// 	content.hobby(),
		// 	content.name(),
		// 	content.budget()
		// );

		bucketItems.forEach(bucket::addBucketItem);

	}
}
