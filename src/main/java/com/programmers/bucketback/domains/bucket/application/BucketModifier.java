package com.programmers.bucketback.domains.bucket.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketItem;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.domains.common.MemberUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketModifier {

	private final BucketReader bucketReader;
	private final BucketAppender bucketAppender;
	private final BucketRemover bucketRemover;
	private final BucketRepository bucketRepository;

	/** 버킷 수정 */
	@Transactional
	public void modify(
		final Long bucketId,
		final BucketContent content
	) {
		List<BucketItem> bucketItems = bucketAppender.createBucketItems(content.itemIds());

		//기존 버킷아이템 내용 삭제
		Bucket bucket = bucketReader.read(bucketId);
		bucket.removeBucketItems();
		bucketRemover.removeBucketItems(bucket.getId());

		bucket.modifyBucket(
			content.hobby(),
			MemberUtils.getCurrentMemberId(),
			content.name(),
			content.budget()
		);
		bucketItems.forEach(bucket::addBucketItem);

		bucketRepository.save(bucket);
	}
}
