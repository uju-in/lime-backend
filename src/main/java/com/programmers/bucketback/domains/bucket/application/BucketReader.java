package com.programmers.bucketback.domains.bucket.application;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.api.dto.response.GetBucketsByCursorResponse;
import com.programmers.bucketback.domains.bucket.application.vo.BucketCursorSummary;
import com.programmers.bucketback.domains.bucket.application.vo.BucketSummary;
import com.programmers.bucketback.domains.bucket.application.vo.CursorPageParameters;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.repository.BucketRepository;
import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BucketReader {

	private final BucketRepository bucketRepository;


	/** 버킷 정보 조회 */
	@Transactional
	public Bucket read(final Long bucketId){
		return bucketRepository.findById(bucketId)
			.orElseThrow(() -> {
			throw new EntityNotFoundException(ErrorCode.BUCKET_NOT_FOUND);
			});
	}

	/**
	 * 버킷 정보 커서 페이징 조회
	 *
	 * @return
	 */
	@Transactional
	public GetBucketsByCursorResponse readByCursor(
		final Long memberId,
		final Hobby hobby,
		final CursorPageParameters parameters
	) {

		// cursorid나 size가 null인 경우도 고려해서 추가해주기
		PageRequest pageRequest = PageRequest.of(0, parameters.size());

		List<BucketSummary> bucketSummaries = bucketRepository.findAllByCursor(
			memberId,
			hobby,
			parameters.cursorId(),
			pageRequest
		);

		List<String> cursorIds = bucketSummaries.stream()
			.map(bucketSummary -> generateCursorId(bucketSummary))
			.toList();

		String nextCursorId = cursorIds.size() == 0 ? null : cursorIds.get(cursorIds.size() - 1);

		List<BucketCursorSummary> bucketCursorSummaries = IntStream.range(0, bucketSummaries.size())
			.mapToObj(i -> {
				String cursorId = cursorIds.get(i);
				BucketSummary bucketSummary = bucketSummaries.get(i);

				return BucketCursorSummary.of(cursorId, bucketSummary);
			}).toList();

		return new GetBucketsByCursorResponse(nextCursorId,bucketCursorSummaries);
	}

	private String generateCursorId(BucketSummary bucketSummary){
		return bucketSummary.createdAt().toString()
			.replace("T","")
			.replace("-","")
			.replace(":","")
			+String.format("%08d",bucketSummary.bucketId());
	}
}
