package com.programmers.bucketback.domains.bucket.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketReader {

	private final BucketRepository bucketRepository;

	public Bucket read(Long bucketId){
		return bucketRepository.findById(bucketId)
			.orElseThrow(() -> {
			throw new BusinessException(ErrorCode.BUCKET_NOT_FOUND);
			});
	}
}
