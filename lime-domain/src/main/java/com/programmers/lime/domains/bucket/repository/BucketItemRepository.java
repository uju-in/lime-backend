package com.programmers.lime.domains.bucket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.bucket.domain.BucketItem;

public interface BucketItemRepository extends JpaRepository<BucketItem,Long> {
	List<BucketItem> findByBucketId(final Long bucketId);
}
