package com.programmers.bucketback.domains.bucket.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketItem;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketModifier {

	private final BucketReader bucketReader;
	private final BucketAppender bucketAppender;

	@Transactional
	public void modify(
		final Long bucketId,
		final BucketContent content
	) {
		List<BucketItem> bucketItems = bucketAppender.createBucketItems(content.itemIds());
		Bucket bucket = bucketReader.read(bucketId);

		bucket.modifyBucket(
			content.hobby(),
			bucketItems,
			content.name(),
			content.budget()
		);
	}
}
