package com.programmers.bucketback.domains.bucket.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.bucket.repository.BucketRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketRemover {

	private final BucketRepository bucketRepository;

	/** 버킷 삭제 */
	public void remove(Long bucketId) {
		bucketRepository.deleteById(bucketId);
	}
}
