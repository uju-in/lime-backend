package com.programmers.bucketback.domains.bucket.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketRemover {

	private final BucketRepository bucketRepository;

	/** 버킷 삭제 */
	public void remove(Long bucketId) {
		bucketRepository.delete(
			bucketRepository.findById(bucketId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.BUCKET_NOT_FOUND))
		);
	}
}
