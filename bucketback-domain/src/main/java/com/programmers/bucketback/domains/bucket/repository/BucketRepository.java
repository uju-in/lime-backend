package com.programmers.bucketback.domains.bucket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.bucket.domain.Bucket;

public interface BucketRepository extends JpaRepository<Bucket, Long>, BucketRepositoryForCursor {
	Optional<Bucket> findByIdAndMemberId(
		final Long bucketId,
		final Long memberId
	);

	List<Bucket> findAllByMemberId(final Long memberId);
}
