package com.programmers.lime.domains.bucket.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.common.model.ItemIdRegistryBuilder;
import com.programmers.lime.domains.bucket.domain.Bucket;
import com.programmers.lime.domains.bucket.domain.BucketBuilder;
import com.programmers.lime.domains.bucket.domain.BucketInfo;
import com.programmers.lime.domains.bucket.domain.BucketItem;
import com.programmers.lime.domains.bucket.repository.BucketRepository;

@ExtendWith(MockitoExtension.class)
public class BucketModifierTest {

	@Mock
	private BucketRepository bucketRepository;

	@Mock
	private BucketRemover bucketRemover;

	@Mock
	private BucketReader bucketReader;

	@Mock
	private BucketAppender bucketAppender;

	@InjectMocks
	private BucketModifier bucketModifier;

	@Test
	@DisplayName("버킷 수정에 성공한다.")
	void modifyBucket() {
		//given
		Long memberId = 1L;
		Long bucketId = 1L;

		BucketInfo updateBucketInfo = BucketBuilder.buildBucketInfo(
			Hobby.BASEBALL,
			"운동장위의무법자",
			200000
		);
		Bucket updateBucket = BucketBuilder.build(updateBucketInfo);
		ItemIdRegistry updateItemRegistry = ItemIdRegistryBuilder.build(Arrays.asList(4L, 5L));
		List<BucketItem> updateBucketItems = BucketBuilder.buildBucketItems(updateItemRegistry);

		// 수정을 위해 기존 bucketItem 제거
		Bucket existBucket = BucketBuilder.build();
		given(bucketReader.read(anyLong(), anyLong()))
			.willReturn(existBucket);
		doNothing().when(bucketRemover)
			.removeBucketItems(anyLong());

		given(bucketAppender.createBucketItems(updateItemRegistry))
			.willReturn(updateBucketItems);
		given(bucketRepository.save(any(Bucket.class)))
			.willReturn(updateBucket);

		//when
		bucketModifier.modify(memberId, bucketId, updateBucketInfo, updateItemRegistry);

		//then
		assertThat(updateBucket.getBucketInfo()).usingRecursiveComparison().isEqualTo(updateBucketInfo);
		assertThat(updateBucketItems.size()).isEqualTo(updateItemRegistry.itemIds().size());
	}
}
