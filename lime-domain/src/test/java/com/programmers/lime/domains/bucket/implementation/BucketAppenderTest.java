package com.programmers.lime.domains.bucket.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.common.model.ItemIdRegistryBuilder;
import com.programmers.lime.domains.bucket.domain.Bucket;
import com.programmers.lime.domains.bucket.domain.BucketBuilder;
import com.programmers.lime.domains.bucket.domain.BucketInfo;
import com.programmers.lime.domains.bucket.repository.BucketRepository;
import com.programmers.lime.domains.item.domain.ItemBuilder;
import com.programmers.lime.domains.item.implementation.ItemReader;

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
		ItemIdRegistry itemIdRegistry = ItemIdRegistryBuilder.build();

		BucketInfo bucketInfo = BucketBuilder.buildBucketInfo();
		Bucket bucket = BucketBuilder.build(bucketInfo);
		Long expectBucketId = bucket.getId();

		given(bucketRepository.save(any(Bucket.class)))
			.willReturn(bucket);
		given(itemReader.read(anyLong()))
			.willReturn(ItemBuilder.build());

		//when
		Long actualBucketId = bucketAppender.append(memberId, bucketInfo, itemIdRegistry);

		//then
		assertThat(actualBucketId).isEqualTo(expectBucketId);
	}

}
