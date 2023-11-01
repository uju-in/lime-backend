package com.programmers.bucketback.domains.bucket.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketItem;
import com.programmers.bucketback.domains.bucket.repository.BucketItemRepository;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.domains.common.MemberUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketRemover {

	private final BucketRepository bucketRepository;
	private final BucketItemRepository bucketItemRepository;
	private final BucketReader bucketReader;
	/** 버킷 삭제 */
	@Transactional
	public void remove(final Long bucketId) {
		Long memberId = MemberUtils.getCurrentMemberId();
		Bucket bucket = bucketReader.read(bucketId, memberId);

		bucketRepository.delete(bucket);
	}

	/** 버킷 아이템 삭제
	 * refactor : orphanRemoval = true 설정에 대해서 알아보고 리팩토링 잡아보기
	 * */
	@Transactional
	public void removeBucketItems(final Long bucketId){
		List<BucketItem> bucketItems = bucketReader.bucketItemRead(bucketId);

		bucketItems.forEach(bucketItem -> bucketItemRepository.delete(bucketItem));
	}
}
