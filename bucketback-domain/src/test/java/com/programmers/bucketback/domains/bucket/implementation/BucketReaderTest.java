package com.programmers.bucketback.domains.bucket.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketBuilder;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
import com.programmers.bucketback.domains.bucket.model.BucketGetServiceResponse;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.domains.item.domain.ItemBuilder;
import com.programmers.bucketback.domains.item.implementation.ItemReader;
import com.programmers.bucketback.domains.item.model.ItemInfo;

@ExtendWith(MockitoExtension.class)
public class BucketReaderTest {

	@Mock
	private BucketRepository bucketRepository;

	@Mock
	private ItemReader itemReader;

	@InjectMocks
	private BucketReader bucketReader;

	@Test
	@DisplayName("버킷 정보 상세 조회")
	void readDetail() {
		//given
		Long bucketId = 1L;
		Bucket bucket = BucketBuilder.build();
		BucketBuilder.setBucketItems(bucket);
		List<ItemInfo> actualItemInfos = ItemBuilder.buildMany(3).stream()
			.map(item -> ItemInfo.from(item))
			.toList();

		given(bucketRepository.findById(anyLong()))
			.willReturn(Optional.of(bucket));
		given(itemReader.read(anyLong()))
			.willReturn(ItemBuilder.build());

		//when
		BucketGetServiceResponse response = bucketReader.readDetail(bucketId);

		//then
		BucketInfo responseBucketInfo = new BucketInfo(response.hobby(), response.name(), response.budget());

		assertThat(responseBucketInfo).usingRecursiveComparison()
			.isEqualTo(bucket.getBucketInfo());
		assertThat(response.itemInfos()).usingRecursiveComparison()
			.ignoringFields("id").isEqualTo(actualItemInfos);
	}

	@Test
	@DisplayName("버킷 정보 커서 페이징 조회")
	void readSummary() {
		//given
		new CursorPageParameters(null, 10);
	}

	@Test
	@DisplayName("버킷 프로필 조회 (3개)")
	void readBucketProfile() {

	}

	@Test
	@DisplayName("버킷 조회와 수정을 위한 memberItem 커서 조회")
	void readBucketMemberItem() {

	}

}
