package com.programmers.bucketback.domains.bucket.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.bucket.api.dto.response.BucketGetResponse;
import com.programmers.bucketback.domains.bucket.api.dto.response.BucketGetByCursorResponse;
import com.programmers.bucketback.domains.bucket.application.vo.BucketContent;
import com.programmers.bucketback.domains.bucket.application.vo.BucketItemContent;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.item.application.ItemReader;
import com.programmers.bucketback.domains.member.application.MemberReader;

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

	/** 버킷 생성 */
	public void createBucket(final BucketContent content) {
		bucketAppender.append(content);
	}

	/** 버킷 수정 */
	public void modifyBucket(
		final Long bucketId,
		final BucketContent content
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		Bucket bucket = bucketReader.read(bucketId, memberId);

		bucketModifier.modify(bucket, content);
	}

	/** 버킷 삭제 */
	public void deleteBucket(final Long bucketId) {
		bucketRemover.remove(bucketId);
	}

	/** 버킷 상세 조회 */
	public BucketGetResponse getBucket(final Long bucketId) {
		Bucket bucket = bucketReader.read(bucketId);

		List<BucketItemContent> bucketItemContents = bucket.getBucketItems().stream()
			.map(bucketItem -> itemReader.read(bucketItem.getItem().getId()))
			.map(item -> BucketItemContent.from(item))
			.toList();

		return new BucketGetResponse(BucketContent.from(bucket), bucketItemContents);
	}

	/** 버킷 커서 조회 */
	public BucketGetByCursorResponse getBucketsByCursor(
		final String nickname,
		final Hobby hobby,
		final CursorPageParameters parameters
	) {
		Long memberId = memberReader.readByNickname(nickname).getId();

		return bucketReader.readByCursor(memberId, hobby, parameters);
	}

}
