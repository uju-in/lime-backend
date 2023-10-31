package com.programmers.bucketback.domains.bucket.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.bucket.api.dto.response.GetBucketResponse;
import com.programmers.bucketback.domains.bucket.api.dto.response.GetBucketsByCursorResponse;
import com.programmers.bucketback.domains.bucket.application.vo.BucketContent;
import com.programmers.bucketback.domains.bucket.application.vo.BucketItemContent;
import com.programmers.bucketback.domains.bucket.application.vo.CursorPageParameters;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.common.Hobby;
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

	/** 버킷 생성 */
	public void createBucket(final BucketContent content) {
		bucketAppender.append(content);
	}

	/** 버킷 수정 */
	public void modifyBucket(
		final Long bucketId,
		final BucketContent content
	) {
		bucketModifier.modify(bucketId, content);
	}

	/** 버킷 삭제 */
	public void deleteBucket(final Long bucketId) {
		bucketRemover.remove(bucketId);
	}

	/** 버킷 상세 조회 */
	public GetBucketResponse getBucket(final Long bucketId) {
		Bucket bucket = bucketReader.read(bucketId);

		return new GetBucketResponse(BucketContent.from(bucket), BucketItemContent.from(bucket));
	}

	/**
	 * 버킷 커서 조회
	 *
	 * @return
	 */
	public GetBucketsByCursorResponse getBucketsByCursor(
		final String nickname,
		final Hobby hobby,
		final CursorPageParameters parameters
	){
		Long memberId = memberReader.readByNickname(nickname).getId();

		return bucketReader.readByCursor(memberId,hobby,parameters);
	}

}
