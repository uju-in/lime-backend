package com.programmers.bucketback.domains.bucket.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
import com.programmers.bucketback.domains.bucket.domain.BucketItem;
import com.programmers.bucketback.domains.bucket.model.ItemIdRegistry;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.implementation.ItemReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketAppender {

	private final BucketRepository bucketRepository;
	private final ItemReader itemReader;

	/** 버킷 생성 */
	@Transactional
	public Long append(
		final Long memberId,
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		List<BucketItem> bucketItems = createBucketItems(registry);

		Bucket bucket = new Bucket(bucketInfo, memberId);
		bucketItems.forEach(bucket::addBucketItem);

		return bucketRepository.save(bucket).getId();
	}

	/** 버킷 아이템 생성 */
	public List<BucketItem> createBucketItems(final ItemIdRegistry registry) {
		return registry.itemIds().stream()
			.map(itemId -> {
				Item item = itemReader.read(itemId);
				BucketItem bucketItem = new BucketItem(item);

				return bucketItem;
			})
			.distinct()
			.collect(Collectors.toList());
	}
}
