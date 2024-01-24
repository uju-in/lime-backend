package com.programmers.lime.domains.review.application;

import java.util.UUID;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.review.application.dto.ReviewGetByCursorServiceResponse;
import com.programmers.lime.domains.review.application.dto.ReviewGetServiceResponse;
import com.programmers.lime.domains.review.domain.Review;
import com.programmers.lime.domains.review.implementation.ReviewAppender;
import com.programmers.lime.domains.review.implementation.ReviewCursorReader;
import com.programmers.lime.domains.review.implementation.ReviewModifier;
import com.programmers.lime.domains.review.implementation.ReviewReader;
import com.programmers.lime.domains.review.implementation.ReviewRemover;
import com.programmers.lime.domains.review.implementation.ReviewStatistics;
import com.programmers.lime.domains.review.model.ReviewContent;
import com.programmers.lime.domains.review.model.ReviewCursorSummary;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.level.PayPoint;
import com.programmers.lime.global.util.MemberUtils;
import com.programmers.lime.s3.S3Manager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private static final String DIRECTORY = "review-images";

	private final ReviewAppender reviewAppender;
	private final ReviewModifier reviewModifier;
	private final ReviewValidator reviewValidator;
	private final ReviewCursorReader reviewCursorReader;
	private final ReviewRemover reviewRemover;
	private final ReviewStatistics reviewStatistics;
	private final MemberUtils memberUtils;
	private final ReviewReader reviewReader;
	private final S3Manager s3Manager;

	@PayPoint(15)
	public Long createReview(
		final Long itemId,
		final ReviewContent reviewContent,
		final List<MultipartFile> multipartReviewImages
	) {
		List<String> reviewImageURLs = uploadReviewImages(multipartReviewImages);

		Long memberId = memberUtils.getCurrentMemberId();
		reviewAppender.append(itemId, memberId, reviewContent, reviewImageURLs);

		return memberId;
	}

	private List<String> uploadReviewImages(final List<MultipartFile> multipartReviewImages) {
		return multipartReviewImages.stream()
			.map(multipartFile -> {
				try {
					String fileType = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
					String imageName = UUID.randomUUID() + fileType;
					s3Manager.uploadFile(multipartFile, DIRECTORY, imageName);

					return s3Manager.getUrl(DIRECTORY, imageName).toString();
				} catch (IOException e) {
					throw new BusinessException(ErrorCode.S3_UPLOAD_FAIL);
				}
			})
			.collect(Collectors.toList());
	}

	public void updateReview(
		final Long itemId,
		final Long reviewId,
		final ReviewContent reviewContent
	) {
		Long memberId = memberUtils.getCurrentMemberId();
		reviewValidator.validItemReview(itemId, reviewId);
		reviewValidator.validOwner(reviewId, memberId);
		reviewModifier.modify(reviewId, reviewContent);
	}

	public ReviewGetByCursorServiceResponse getReviewsByCursor(
		final Long itemId,
		final CursorPageParameters parameters
	) {
		int reviewCount = reviewStatistics.getReviewCount(itemId);
		Long memberId = memberUtils.getCurrentMemberId();
		CursorSummary<ReviewCursorSummary> cursorSummary = reviewCursorReader.readByCursor(
			itemId,
			memberId,
			parameters
		);

		return new ReviewGetByCursorServiceResponse(reviewCount, cursorSummary);
	}

	public void deleteReview(
		final Long itemId,
		final Long reviewId
	) {
		Long memberId = memberUtils.getCurrentMemberId();
		reviewValidator.validItemReview(itemId, reviewId);
		reviewValidator.validOwner(reviewId, memberId);
		reviewRemover.remove(reviewId);
	}

	public ReviewGetServiceResponse getReview(
		final Long itemId,
		final Long reviewId
	) {
		reviewValidator.validItemReview(itemId, reviewId);
		Review review = reviewReader.read(reviewId);

		return new ReviewGetServiceResponse(
			review.getId(),
			review.getRating(),
			review.getContent()
		);
	}
}
