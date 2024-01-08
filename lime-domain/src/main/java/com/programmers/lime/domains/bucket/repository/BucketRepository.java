package com.programmers.lime.domains.bucket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.bucket.domain.Bucket;

public interface BucketRepository extends JpaRepository<Bucket, Long>, BucketRepositoryForCursor {
	Optional<Bucket> findByIdAndMemberId(
		final Long bucketId,
		final Long memberId
	);

	List<Bucket> findAllByMemberId(final Long memberId);

	int countByMemberId(final Long memberId);

	@Query(
		"""
				SELECT COUNT(b)
			  	FROM Bucket b
			  	WHERE b.bucketInfo.hobby = :hobby AND b.memberId = :memberId
			"""
	)
	int countByHobbyAndMemberId(
		final Hobby hobby,
		final Long memberId
	);
}
