package com.programmers.lime.domains.bucket.application;

import org.springframework.stereotype.Service;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.domains.bucket.application.dto.response.BucketGetCursorServiceResponse;
import com.programmers.lime.domains.bucket.application.dto.response.BucketGetMemberItemServiceResponse;
import com.programmers.lime.domains.bucket.domain.BucketInfo;
import com.programmers.lime.domains.bucket.implementation.BucketAppender;
import com.programmers.lime.domains.bucket.implementation.BucketModifier;
import com.programmers.lime.domains.bucket.implementation.BucketReader;
import com.programmers.lime.domains.bucket.implementation.BucketRemover;
import com.programmers.lime.domains.bucket.model.BucketGetServiceResponse;
import com.programmers.lime.domains.bucket.model.BucketMemberItemSummary;
import com.programmers.lime.domains.bucket.model.BucketSummary;
import com.programmers.lime.domains.item.implementation.MemberItemReader;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketService {

	private final BucketAppender bucketAppender;
	private final BucketModifier bucketModifier;
	private final BucketRemover bucketRemover;
	private final BucketReader bucketReader;
	private final MemberReader memberReader;
	private final MemberItemReader memberItemReader;
	private final MemberUtils memberUtils;

	/** 버킷 생성 */
	public Long createBucket(
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		validateEmptyRegistry(registry);
		Long memberId = memberUtils.getCurrentMemberId();

		return bucketAppender.append(memberId, bucketInfo, registry);
	}

	/** 버킷 수정 */
	public void modifyBucket(
		final Long bucketId,
		final BucketInfo bucketInfo,
		final ItemIdRegistry registry
	) {
		validateEmptyRegistry(registry);

		bucketModifier.modify(bucketId, bucketInfo, registry);
	}

	/** 버킷 삭제 */
	public void deleteBucket(final Long bucketId) {
		Long memberId = memberUtils.getCurrentMemberId();
		bucketRemover.remove(bucketId, memberId);
	}

	/**
	 * 버킷 조회 수정을 위한 멤버 아이템 목록 조회
	 */
	public BucketGetMemberItemServiceResponse getMemberItemsForModify(
		final Long bucketId,
		final Hobby hobby,
		final CursorPageParameters parameters
	) {
		Long memberId = memberUtils.getCurrentMemberId();

		int totalMemberItemCount = memberItemReader.countByMemberIdAndHobby(memberId, hobby);
		CursorSummary<BucketMemberItemSummary> cursorSummary = bucketReader.readByMemberItems(
			bucketId,
			memberId,
			hobby,
			parameters
		);

		return new BucketGetMemberItemServiceResponse(cursorSummary, totalMemberItemCount);
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

		int totalBucketCount = bucketReader.countByMemberIdAndHobby(memberId, hobby);
		CursorSummary<BucketSummary> cursorSummary = bucketReader.readByCursor(
			memberId,
			hobby,
			parameters
		);

		return new BucketGetCursorServiceResponse(cursorSummary, totalBucketCount);
	}

	private void validateEmptyRegistry(final ItemIdRegistry registry) {
		if (registry.itemIds().isEmpty()) {
			throw new BusinessException(ErrorCode.BUCKET_ITEM_NOT_REQUESTED);
		}
	}

}
