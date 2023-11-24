package com.programmers.bucketback.domains.bucket.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.common.model.ItemIdRegistry;
import com.programmers.bucketback.common.model.ItemIdRegistryBuilder;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketBuilder;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
import com.programmers.bucketback.domains.bucket.domain.BucketItem;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.ItemBuilder;

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
			Hobby.CYCLE,
			"도로위의무법자",
			200000
		);
		ItemIdRegistry updateItemIds = ItemIdRegistryBuilder.build(Arrays.asList(4L, 5L));
		Bucket updateBucket = BucketBuilder.build(updateBucketInfo);

		Bucket existBucket = BucketBuilder.build();

		// 수정을 위해 기존 bucketItem 제거
		given(bucketReader.read(anyLong(), anyLong()))
			.willReturn(existBucket);
		doNothing().when(bucketRemover)
			.removeBucketItems(any(Long.class));

		// 업데이트를 위한 새로운 데이터 생성
		List<BucketItem> updateBucketItems = new ArrayList<>();
		for (Long itemId : updateItemIds.itemIds()) {
			Item updatedItem = ItemBuilder.build(itemId);
			updateBucketItems.add(new BucketItem(updatedItem));
		}

		given(bucketAppender.createBucketItems(updateItemIds))
			.willReturn(updateBucketItems);
		given(bucketRepository.save(any(Bucket.class)))
			.willReturn(updateBucket);

		//when
		bucketModifier.modify(memberId, bucketId, updateBucketInfo, updateItemIds);

		//then
		assertThat(updateBucket.getBucketInfo()).usingRecursiveComparison().isEqualTo(updateBucketInfo);
		assertThat(updateBucketItems.size()).isEqualTo(updateItemIds.itemIds().size());
	}
}
