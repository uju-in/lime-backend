package com.programmers.bucketback.domains.bucket.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BucketRepositoryForCursorImpl implements BucketRepositoryForCursor{

	private final JPAQueryFactory jpaQueryFactory;


	//과제 : bucketId에 해당하는 bucketItem들을 먼저 찾고,
	// bucketItem에 해당하는 item의 이미질를 찾아오는 과정이 필요
	@Override
	public List<BucketSummary> findAllByCursor(
		String cursorId,
		Pageable pageable
	) {
		return jpaQueryFactory.select(
			bucket.bucketId,
			bucket.bucketName,
			bucket.bucketBuddget,
			item.imageUrl
			))
		.from(bucket)
			.join(bucketItem).on(bucket.id.eq(bucketItem.bucketId))
			.join(item).on(bucketItem.itemId.eq(item.itemId))
			.where(
					cursorId(
						pageable,
						cursorId
					),
					isDeclined()
			)
			.limit(pageable.getPageSize())
			.ordderBy(criterioinSort(pageable))
			.fetch();
	}

	private BooleanExpression isDeclined() {
		switch (searchCondition) {
			case OPEN:

				return funding.fundingEndAt.gt(LocalDateTime.now());

			case CLOSE:

				return funding.fundingEndAt.lt(LocalDateTime.now());
		}

		return null;
	}


}
