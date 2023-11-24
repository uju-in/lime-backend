package com.programmers.bucketback.domains.bucket.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.common.model.ItemIdRegistry;
import com.programmers.bucketback.domains.bucket.builder.BucketBuilder;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.domains.item.builder.domain.ItemBuilder;
import com.programmers.bucketback.domains.item.implementation.ItemReader;

@ExtendWith(MockitoExtension.class)
public class BucketAppenderTest {

	@Mock
	private BucketRepository bucketRepository;

	@Mock
	private ItemReader itemReader;

	@InjectMocks
	private BucketAppender bucketAppender;

	@Test
	@DisplayName("버킷을 생성한다.")
	void createBucket() {
		//given
		Long memberId = 1L;
		BucketInfo bucketInfo = BucketBuilder.buildBucketInfo();
		ItemIdRegistry itemIdRegistry = BucketBuilder.createItemIdRegistry();

		Bucket bucket = BucketBuilder.build();
		Long expectedBucketId = bucket.getId();
		given(bucketRepository.save(any(Bucket.class)))
			.willReturn(bucket);
		given(itemReader.read(any(Long.class)))
			.willReturn(ItemBuilder.build());

		//when
		Long actualBucketId = bucketAppender.append(memberId, bucketInfo, itemIdRegistry);

		//then
		assertThat(actualBucketId).isEqualTo(expectedBucketId);
	}

}
