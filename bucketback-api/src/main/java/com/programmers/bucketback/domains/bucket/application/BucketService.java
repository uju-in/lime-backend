package com.programmers.bucketback.domains.bucket.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.common.model.ItemIdRegistry;
import com.programmers.bucketback.domains.bucket.application.dto.response.BucketGetCursorServiceResponse;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
import com.programmers.bucketback.domains.bucket.implementation.BucketAppender;
import com.programmers.bucketback.domains.bucket.implementation.BucketModifier;
import com.programmers.bucketback.domains.bucket.implementation.BucketReader;
import com.programmers.bucketback.domains.bucket.implementation.BucketRemover;
import com.programmers.bucketback.domains.bucket.model.BucketGetServiceResponse;
import com.programmers.bucketback.domains.bucket.model.BucketMemberItemSummary;
import com.programmers.bucketback.domains.bucket.model.BucketSummary;
import com.programmers.bucketback.domains.item.implementation.ItemReader;
import com.programmers.bucketback.domains.member.implementation.MemberReader;
import com.programmers.bucketback.error.BusinessException;
import com.programmers.bucketback.error.ErrorCode;
import com.programmers.bucketback.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketService {

	private final BucketAppender bucketAppender;
	private final BucketModifier bucketModifier;
	private final BucketRemover bucketRemover;
	private final BucketReader bucketReader;
	private final MemberReader memberReader;
	private final ItemReader itemReader;
	private final MemberUtils memberUtils;

	/** 버킷 생성 */
	public Long createBucket(
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		validateEmptyRegistry(registry);
		Long memberId = memberUtils.getCurrentMemberId();
		validateExceedBudget(bucketInfo, registry);

		return bucketAppender.append(memberId, bucketInfo, registry);
	}

	/** 버킷 수정 */
	public void modifyBucket(
		final Long bucketId,
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		validateEmptyRegistry(registry);
		validateExceedBudget(bucketInfo, registry);

		Long memberId = memberUtils.getCurrentMemberId();
		bucketModifier.modify(memberId, bucketId, bucketInfo, registry);
	}

	/** 버킷 삭제 */
	public void deleteBucket(final Long bucketId) {
		Long memberId = memberUtils.getCurrentMemberId();
		bucketRemover.remove(bucketId, memberId);
	}

	/**
	 * 버킷 조회 수정을 위한 멤버 아이템 목록 조회
	 */
	public CursorSummary<BucketMemberItemSummary> getMemberItemsForModify(
		final Long bucketId,
		final Hobby hobby,
		final CursorPageParameters parameters
	) {
		Long memberId = memberUtils.getCurrentMemberId();

		return bucketReader.readByMemberItems(
			bucketId,
			memberId,
			hobby,
			parameters
		);
	}

	/**
	 * 버킷 상세 조회
	 */
	public BucketGetServiceResponse getBucket(final Long bucketId) {
		return bucketReader.readDetail(bucketId);
	}

	/**
	 * 버킷 커서 조회
	 */
	public BucketGetCursorServiceResponse getBucketsByCursor(
		final String nickname,
		final Hobby hobby,
		final CursorPageParameters parameters
	) {
		Long memberId = memberReader.readByNickname(nickname).getId();

		int totalBucketCount = bucketReader.countByMemberId(memberId);
		CursorSummary<BucketSummary> cursorSummary = bucketReader.readByCursor(
			memberId,
			hobby,
			parameters
		);

		return BucketGetCursorServiceResponse.of(cursorSummary, totalBucketCount);
	}

	private void validateExceedBudget(
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		if (bucketInfo.getBudget() != null) {
			int totalPrice = registry.itemIds().stream()
				.map(itemId -> itemReader.read(itemId).getPrice())
				.reduce(0, Integer::sum);

			bucketInfo.validateBucketBudget(totalPrice, bucketInfo.getBudget());
		}
	}

	private void validateEmptyRegistry(final ItemIdRegistry registry) {
		if (registry.itemIds().isEmpty()) {
			throw new BusinessException(ErrorCode.BUCKET_ITEM_NOT_REQUESTED);
		}
	}

}
