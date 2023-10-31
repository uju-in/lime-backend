package com.programmers.bucketback.domains.bucket.application;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

	/** 버킷 정보 커서 페이징 조회 */
	@Transactional
	public void readByCursor(
		final String nickname,
		final Hobby hobby,
		final CursorPageParameters parameters
	) {

		PageRequest pageRequest = PageRequest.of(
			0,
			parameters.size()
		);

		// 커서 페이징 로직 추가하기
		List<BucketSummary> bucketSummaries = bucketRepository.findAllByCursor(
			parameters.cursorId(),
			pageRequest
		);

		//가공해온 결과를 바탕으로 응답 값을 만들어내야함.
		// result.get,

		List<String> cursorIds = bucketSummaries.stream()
			.map(bucketSummary -> {
				generateCursor(bucketSummary);
			})
			.toList();

		String nextCursorId = cursorIds.size() == 0 ? null : cursorIds.get(cursorIds.size() - 1);

		//실제 응답 데이터 형식을 어떻게 정의하면 좋을지 그 내용을 맞추는 부분
	}

	// bucketId (생성시간도 필요 없을듯)
	// private String generateCursorId(BucketSummary bucketSummary){
	// 	String.format("%012d",bucketSummary.) // bucketId
	// }
}
