package com.programmers.bucketback.domains.bucket.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketReader {

	private final BucketRepository bucketRepository;

	/** 버킷 정보 조회 */
	@Transactional
	public Bucket read(Long bucketId){
		return bucketRepository.findById(bucketId)
			.orElseThrow(() -> {
			throw new EntityNotFoundException(ErrorCode.BUCKET_NOT_FOUND);
			});
	}
}
