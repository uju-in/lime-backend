package com.programmers.lime.domains.review.application;

import java.io.IOException;
import java.util.Collections;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.review.application.dto.ReviewGetByCursorServiceResponse;
import com.programmers.lime.domains.review.application.dto.ReviewGetServiceResponse;
import com.programmers.lime.domains.review.domain.Review;
import com.programmers.lime.domains.review.implementation.ReviewAppender;
import com.programmers.lime.domains.review.implementation.ReviewCursorReader;
import com.programmers.lime.domains.review.implementation.ReviewImageAppender;
import com.programmers.lime.domains.review.implementation.ReviewImageRemover;
import com.programmers.lime.domains.review.implementation.ReviewLikeRemover;
import com.programmers.lime.domains.review.implementation.ReviewModifier;
import com.programmers.lime.domains.review.implementation.ReviewReader;
import com.programmers.lime.domains.review.implementation.ReviewRemover;
import com.programmers.lime.domains.review.implementation.ReviewStatistics;
import com.programmers.lime.domains.review.model.ReviewContent;
import com.programmers.lime.domains.review.model.ReviewCursorSummary;
import com.programmers.lime.domains.review.model.ReviewSortCondition;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.event.point.PointEvent;
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
	private final ApplicationEventPublisher applicationEventPublisher;
	private final ReviewImageAppender reviewImageAppender;
	private final ReviewLikeRemover reviewLikeRemover;
	private final ReviewImageRemover reviewImageRemover;

	@Transactional
	public void createReview(
		final Long itemId,
		final ReviewContent reviewContent,
		final List<MultipartFile> multipartReviewImages
	) {
		Long memberId = memberUtils.getCurrentMemberId();

		reviewValidator.validIsMemberAlreadyReviewed(itemId, memberId);

		Long reviewId = reviewAppender.append(itemId, memberId, reviewContent);

		applicationEventPublisher.publishEvent(new PointEvent(memberId, 15));

		List<String> reviewImageURLs = uploadReviewImages(multipartReviewImages);
		reviewImageAppender.append(reviewId, reviewImageURLs);
	}

	private List<String> uploadReviewImages(final List<MultipartFile> multipartReviewImages) {
		if (multipartReviewImages == null || multipartReviewImages.isEmpty()) {
			return Collections.emptyList();
		}

		if (multipartReviewImages.size() > 5) {
			throw new BusinessException(ErrorCode.REVIEW_IMAGE_COUNT_EXCEEDED);
		}

		return multipartReviewImages.stream()
			.filter(multipartFile -> {
				if (multipartFile.getOriginalFilename() == null) {
					return false;
				}
				return !multipartFile.getOriginalFilename().isEmpty();
			})
			.map(multipartFile -> {
				try {
					String fileType = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
					String imageName = UUID.randomUUID() + "." + fileType;

					s3Manager.uploadFile(multipartFile, DIRECTORY, imageName);
					return s3Manager.getUrl(DIRECTORY, imageName).toString();
				} catch (IOException e) {
					throw new BusinessException(ErrorCode.S3_UPLOAD_FAIL);
				}
			})
			.collect(Collectors.toList());
	}

	@Transactional
	public void updateReview(
		final Long reviewId,
		final ReviewContent reviewContent,
		final List<String> reviewItemUrlsToRemove,
		final List<MultipartFile> multipartReviewImages
	) {
		Long memberId = memberUtils.getCurrentMemberId();

		reviewValidator.validOwner(reviewId, memberId);
		reviewModifier.modify(reviewId, reviewContent);

		reviewValidator.validReviewItemUrlsToRemove(reviewId, reviewItemUrlsToRemove);
		reviewImageRemover.removeReviewImagesImageUrls(reviewItemUrlsToRemove);

		removeByReviewImageUrls(reviewItemUrlsToRemove);

		List<String> reviewImageURLs = uploadReviewImages(multipartReviewImages);
		reviewImageAppender.append(reviewId, reviewImageURLs);
	}

	public ReviewGetByCursorServiceResponse getReviewsByCursor(
		final Long itemId,
		final CursorPageParameters parameters,
		final String reviewSortCondition
	) {
		ReviewSortCondition sortCondition = ReviewSortCondition.from(reviewSortCondition);
		int reviewCount = reviewStatistics.getReviewCount(itemId);
		Long memberId = memberUtils.getCurrentMemberId();
		CursorSummary<ReviewCursorSummary> cursorSummary = reviewCursorReader.readByCursor(
			itemId,
			memberId,
			parameters,
			sortCondition
		);

		return new ReviewGetByCursorServiceResponse(reviewCount, cursorSummary);
	}

	@Transactional
	public void deleteReview(
		final Long reviewId
	) {
		Long memberId = memberUtils.getCurrentMemberId();
		reviewValidator.validOwner(reviewId, memberId);
		reviewLikeRemover.deleteByReviewId(reviewId);
		reviewImageRemover.deleteByReviewId(reviewId);
		reviewRemover.remove(reviewId);
	}

	public void removeByReviewImageUrls(final List<String> reviewImageUrls) {

		if(reviewImageUrls == null) {
			return;
		}

		try {
			for (String reviewImageUrl : reviewImageUrls) {
				s3Manager.deleteObjectByUrl(reviewImageUrl);
			}
		} catch (MalformedURLException e) {
			throw new BusinessException(ErrorCode.BAD_REVIEW_IMAGE_URL);
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.S3_DELETE_FAIL);
		}
	}

	public ReviewGetServiceResponse getReview(
		final Long reviewId
	) {
		Review review = reviewReader.read(reviewId);

		return new ReviewGetServiceResponse(
			review.getId(),
			review.getRating(),
			review.getContent()
		);
	}
}
