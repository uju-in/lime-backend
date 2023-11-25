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
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.cursor.CursorUtils;
import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketBuilder;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
import com.programmers.bucketback.domains.bucket.domain.BucketSummaryBuilder;
import com.programmers.bucketback.domains.bucket.model.BucketGetServiceResponse;
import com.programmers.bucketback.domains.bucket.model.BucketProfile;
import com.programmers.bucketback.domains.bucket.model.BucketSummary;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.domains.item.domain.ItemBuilder;
import com.programmers.bucketback.domains.item.implementation.ItemReader;
import com.programmers.bucketback.domains.item.implementation.MemberItemReader;
import com.programmers.bucketback.domains.item.model.ItemInfo;

@ExtendWith(MockitoExtension.class)
public class BucketReaderTest {

	@Mock
	private BucketRepository bucketRepository;

	@Mock
	private ItemReader itemReader;

	@Mock
	private MemberItemReader memberItemReader;

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
		Long memberId = 1L;
		Hobby hobby = Hobby.BASKETBALL;
		CursorPageParameters parameters = new CursorPageParameters(null, 10); // 이후 생성된 model로 대체 예정
		List<BucketSummary> bucketSummaries = BucketSummaryBuilder.buildMany(parameters.size());
		CursorSummary<BucketSummary> expectedCursorSummary = CursorUtils.getCursorSummaries(bucketSummaries);

		given(bucketRepository.findAllByCursor(memberId, hobby, parameters.cursorId(),
			parameters.size())) // 궁금한 점 anyInt는 왜 안되는가?
			.willReturn(bucketSummaries);

		//when
		CursorSummary<BucketSummary> actualBucketSummaries = bucketReader.readByCursor(memberId, hobby, parameters);

		//then
		assertThat(expectedCursorSummary).usingRecursiveComparison()
			.isEqualTo(actualBucketSummaries);
	}

	@Test
	@DisplayName("버킷 프로필 조회 취미별로 (최대 3개)")
	void readBucketProfile() {
		//given
		Long memberId = 1L;
		BucketInfo bucketInfo1 = BucketBuilder.buildBucketInfo(
			Hobby.BASKETBALL,
			"유러피안 농구",
			100000
		);
		BucketInfo bucketInfo2 = BucketBuilder.buildBucketInfo(
			Hobby.BASKETBALL,
			"nba 농구",
			200000
		);
		BucketInfo bucketInfo3 = BucketBuilder.buildBucketInfo(
			Hobby.BASEBALL,
			"메이저리그 야구",
			100001
		);
		Bucket bucket1 = BucketBuilder.build(bucketInfo1);
		Bucket bucket2 = BucketBuilder.build(bucketInfo2);
		Bucket bucket3 = BucketBuilder.build(bucketInfo3);
		List<Bucket> buckets = List.of(bucket1, bucket2, bucket3);

		given(bucketRepository.findAllByMemberId(anyLong())).willReturn(buckets);

		//when
		List<BucketProfile> bucketProfiles = bucketReader.readBucketProfile(memberId);

		//then
		assertThat(bucketProfiles.size()).isEqualTo(2);
	}

	@Test
	@DisplayName("버킷 조회와 수정을 위한 memberItem 커서 조회")
	void readBucketMemberItem() {

	}

}
