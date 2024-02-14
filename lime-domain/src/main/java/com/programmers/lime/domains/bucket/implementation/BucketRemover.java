package com.programmers.lime.domains.bucket.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.bucket.domain.Bucket;
import com.programmers.lime.domains.bucket.domain.BucketItem;
import com.programmers.lime.domains.bucket.repository.BucketItemRepository;
import com.programmers.lime.domains.bucket.repository.BucketRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketRemover {

	private final BucketRepository bucketRepository;
	private final BucketItemRepository bucketItemRepository;
	private final BucketReader bucketReader;

	/** 버킷 삭제 */
	@Transactional
	public void remove(
		final Long bucketId,
		final Long memberId
	) {
		Bucket bucket = bucketReader.read(bucketId, memberId);

		bucketItemRepository.deleteAll(bucketId);
		bucketRepository.delete(bucket);
	}

	/** 버킷 아이템 삭제 */
	public void removeBucketItems(final Long bucketId) {
		bucketItemRepository.deleteAll(bucketId);
	}
}
