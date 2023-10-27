package com.programmers.bucketback.domains.bucket.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketItem;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.repository.ItemRepository;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketAppender {

	private final BucketRepository bucketRepository;
	private final ItemRepository itemRepository;

	@Transactional
	public void append(final BucketContent content) {
		List<BucketItem> bucketItems = createBucketItems(content.itemIds());

		Bucket bucket = Bucket.builder()
			// .memberId(memberId) // memberId값 필요함
			.hobby(content.hobby())
			.name(content.name())
			.budget(content.budget())
			.build();
		bucketItems.forEach(bucket::addBucketItem);

		bucketRepository.save(bucket);
	}

	public List<BucketItem> createBucketItems(final List<Long> itemIds){
		//itemReader와 같이 병곤이가 사용하는 로직이 있으면 대체 예정
		return itemIds.stream()
			.map(id ->
				{
					Item item = itemRepository.findById(id)
						.orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR)); //병곤 예외 있으면 변경예정
					BucketItem bucketItem = new BucketItem(item);

					return bucketItem;
				}
			).distinct()
			.collect(Collectors.toList());
	}
}
