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
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketAppender {

	private final BucketItemRepository bucketItemRepository;
	private final BucketRepository bucketRepository;
	private final ItemReader itemReader;

	/** 버킷 생성 */
	@Transactional
	public Long append(
		final Long memberId,
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		Bucket bucket = new Bucket(bucketInfo, memberId);
		Long bucketId = bucketRepository.save(bucket).getId();

		List<BucketItem> bucketItems = createBucketItems(registry,bucketId);

		bucketItemRepository.saveAll(bucketItems);

		return bucketId;
	}

	/** 버킷 아이템 생성 */
	public List<BucketItem> createBucketItems(
		final ItemIdRegistry registry,
		final Long bucketId
	) {
		if(itemReader.existsAll(registry.itemIds())){
			return registry.itemIds().stream()
				.distinct()
				.map(itemId -> {
					return new BucketItem(itemId, bucketId);
				}).toList();
		}

		throw new BusinessException(ErrorCode.ITEM_NOT_FOUND);
	}
}
