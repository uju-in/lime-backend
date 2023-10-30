package com.programmers.bucketback.domains.bucket.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.domain.BucketItem;
import com.programmers.bucketback.domains.bucket.repository.BucketItemRepository;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketRemover {

	private final BucketRepository bucketRepository;
	private final BucketItemRepository bucketItemRepository;

	/** 버킷 삭제 */
	@Transactional
	public void remove(Long bucketId) {
		bucketRepository.delete(
			bucketRepository.findById(bucketId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.BUCKET_NOT_FOUND))
		);
	}

	/** 버킷 아이템 삭제
	 * refactor : orphanRemoval = true 설정에 대해서 알아보고 리팩토링 잡아보기
	 * */
	@Transactional
	public void removeBucketItems(Long bucketId){
		List<BucketItem> bucketItems = bucketItemRepository.findByBucketId(bucketId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.BUCKET_ITEM_NOT_FOUND));

		bucketItems.forEach(bucketItem -> bucketItemRepository.delete(bucketItem));
	}
}
