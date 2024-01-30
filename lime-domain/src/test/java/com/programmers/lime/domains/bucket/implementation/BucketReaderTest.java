package com.programmers.lime.domains.bucket.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.domains.bucket.domain.Bucket;
import com.programmers.lime.domains.bucket.domain.BucketBuilder;
import com.programmers.lime.domains.bucket.domain.BucketInfo;
import com.programmers.lime.domains.bucket.domain.BucketItem;
import com.programmers.lime.domains.bucket.model.BucketGetServiceResponse;
import com.programmers.lime.domains.bucket.model.BucketMemberItemSummary;
import com.programmers.lime.domains.bucket.model.BucketMemberItemSummaryBuilder;
import com.programmers.lime.domains.bucket.model.BucketProfile;
import com.programmers.lime.domains.bucket.model.BucketSummary;
import com.programmers.lime.domains.bucket.model.BucketSummaryBuilder;
import com.programmers.lime.domains.bucket.repository.BucketItemRepository;
import com.programmers.lime.domains.bucket.repository.BucketRepository;
import com.programmers.lime.domains.item.domain.ItemBuilder;
import com.programmers.lime.domains.item.domain.MemberItem;
import com.programmers.lime.domains.item.domain.MemberItemBuilder;
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.domains.item.implementation.MemberItemReader;
import com.programmers.lime.domains.item.model.ItemInfo;

@ExtendWith(MockitoExtension.class)
public class BucketReaderTest {

	@Mock
	private BucketItemRepository bucketItemRepository;

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
		List<ItemInfo> actualItemInfos = ItemBuilder.buildMany().stream()
			.map(item -> ItemInfo.from(item))
			.toList();

		List<BucketItem> bucketItems = BucketBuilder.buildBucketItems(
			bucketId,
			new ItemIdRegistry(Arrays.asList(1L, 2L, 3L, 4L))
		);

		given(bucketItemRepository.findAllByBucketId(anyLong()))
			.willReturn(bucketItems);
		given(bucketRepository.findById(anyLong())).willReturn(Optional.of(bucket));
		given(itemReader.readAll(anyList())).willReturn(ItemBuilder.buildMany());

		//when
		BucketGetServiceResponse response = bucketReader.readDetail(bucketId);
		BucketInfo responseBucketInfo = BucketBuilder.buildBucketInfo(
			response.hobby(),
			response.name(),
			response.budget()
		);

		//then
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

		given(bucketRepository.findAllByCursor(
			anyLong(),
			any(Hobby.class),
			eq(parameters.cursorId()),
			eq(parameters.size())
		))
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
	@DisplayName("버킷 생성 수정을 위한 memberItem 커서 조회")
	void readBucketMemberItem() {
		//given
		Long bucketId = 1L;
		Long memberId = 1L;
		Hobby hobby = Hobby.BASKETBALL;
		CursorPageParameters parameters = new CursorPageParameters(null, 2); // 이후 생성된 model로 대체 예정
		List<MemberItem> memberItems = MemberItemBuilder.buildMany();

		List<BucketMemberItemSummary> expectSummaries = BucketMemberItemSummaryBuilder.buildMany(3);
		CursorSummary<BucketMemberItemSummary> expectCursorSummary = CursorUtils.getCursorSummaries(expectSummaries);

		given(memberItemReader.readByMemberId(anyLong())).willReturn(memberItems);
		given(memberItemReader.readBucketMemberItem(
			anyList(),
			anyList(),
			any(Hobby.class),
			anyLong(),
			any(),
			eq(parameters.size())
		)).willReturn(expectSummaries);

		//when
		CursorSummary<BucketMemberItemSummary> actualSummary =
			bucketReader.readByMemberItems(bucketId, memberId, hobby, parameters);

		//then
		assertThat(actualSummary).usingRecursiveComparison()
			.isEqualTo(expectCursorSummary);
	}

}
