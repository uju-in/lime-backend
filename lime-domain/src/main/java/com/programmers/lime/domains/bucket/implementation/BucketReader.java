package com.programmers.lime.domains.bucket.implementation;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.bucket.domain.Bucket;
import com.programmers.lime.domains.bucket.domain.BucketItem;
import com.programmers.lime.domains.bucket.model.BucketGetServiceResponse;
import com.programmers.lime.domains.bucket.model.BucketMemberItemSummary;
import com.programmers.lime.domains.bucket.model.BucketProfile;
import com.programmers.lime.domains.bucket.model.BucketSummary;
import com.programmers.lime.domains.bucket.repository.BucketItemRepository;
import com.programmers.lime.domains.bucket.repository.BucketRepository;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.domains.item.implementation.MemberItemReader;
import com.programmers.lime.domains.item.model.ItemInfo;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BucketReader {

	private static final int ITEM_IMAGE_LIMIT = 4;
	private static final int BUCKET_PROFILE_LIMIT = 3;
	private static final int DEFAULT_PAGING_SIZE = 20;

	private final BucketRepository bucketRepository;
	private final BucketItemRepository bucketItemRepository;
	private final MemberItemReader memberItemReader;
	private final ItemReader itemReader;

	/** 버킷 정보 조회 */
	public Bucket read(final Long bucketId) {
		return bucketRepository.findById(bucketId)
			.orElseThrow(() -> {
				throw new EntityNotFoundException(ErrorCode.BUCKET_NOT_FOUND);
			});
	}

	public Bucket read(
		final Long bucketId,
		final Long memberId
	) {
		return bucketRepository.findByIdAndMemberId(bucketId, memberId)
			.orElseThrow(() -> {
				throw new EntityNotFoundException(ErrorCode.BUCKET_NOT_FOUND);
			});
	}

	public List<Bucket> readByMemberId(final Long memberId) {
		return bucketRepository.findAllByMemberId(memberId);
	}

	/** 버킷 아이템 정보 조회 */
	public List<BucketItem> readBucketItems(final Long bucketId) {
		return bucketItemRepository.findAllByBucketId(bucketId);
	}

	/** 버킷 조회와 수정을 위한 MemberItem 커서 조회 */
	public CursorSummary<BucketMemberItemSummary> readByMemberItems(
		final Long bucketId,
		final Long memberId,
		final Hobby hobby,
		final CursorPageParameters parameters
	) {
		int pageSize = getPageSize(parameters);

		List<Long> itemIdsFromMemberItem = memberItemReader.readByMemberId(memberId).stream()
			.map(memberItem -> memberItem.getItem().getId())
			.toList();
		List<Long> itemIds = null;

		if (bucketId != null) {
			itemIds = getItemIds(bucketId);
		}

		List<BucketMemberItemSummary> summaries = memberItemReader.readBucketMemberItem(
			itemIds,
			itemIdsFromMemberItem,
			hobby,
			memberId,
			parameters.cursorId(),
			pageSize
		);

		return CursorUtils.getCursorSummaries(summaries);
	}

	/** 버킷 정보 커서 페이징 조회 */
	public CursorSummary<BucketSummary> readByCursor(
		final Long memberId,
		final Hobby hobby,
		final CursorPageParameters parameters
	) {
		int pageSize = getPageSize(parameters);

		List<BucketSummary> summaries = bucketRepository.findAllByCursor(
			memberId,
			hobby,
			parameters.cursorId(),
			pageSize
		);

		return CursorUtils.getCursorSummaries(summaries);
	}

	/** 버킷 정보 상세 조회 */
	public BucketGetServiceResponse readDetail(final Long bucketId) {
		Bucket bucket = read(bucketId);
		List<Long> itemIds = getItemIds(bucketId);

		List<ItemInfo> itemInfos = itemReader.readAll(itemIds).stream()
			.map(item -> ItemInfo.from(item))
			.toList();

		int totalPrice = itemInfos.stream()
			.mapToInt(ItemInfo::price)
			.sum();

		return BucketGetServiceResponse.of(bucket, totalPrice, itemInfos);
	}


	/** 마이페이지를 위한 버킷 프로필 조회 (3개) */
	public List<BucketProfile> readBucketProfile(final Long memberId) {
		List<Bucket> buckets = readByMemberId(memberId);

		return selectBucketProfile(buckets);
	}

	private List<BucketProfile> selectBucketProfile(final List<Bucket> buckets) {
		List<Bucket> selectedBuckets = selectBucketsByHobby(buckets);

		return selectedBuckets.stream()
			.map(bucket -> {
				List<String> itemImages = extractBucketItemImages(bucket.getId());

				return BucketProfile.of(bucket, itemImages);
			})
			.toList();
	}

	private List<Bucket> selectBucketsByHobby(final List<Bucket> selectedBuckets) {
		return selectedBuckets.stream()
			.collect(Collectors.groupingBy(Bucket::getHobby))
			.values().stream()
			.map(group -> group.stream()
				.max(Comparator.comparing(Bucket::getId))
				.orElse(null))
			.limit(BUCKET_PROFILE_LIMIT)
			.toList();
	}

	private List<String> extractBucketItemImages(final Long bucketId) {
		List<BucketItem> bucketItems = readBucketItems(bucketId);

		return bucketItems.stream()
			.limit(ITEM_IMAGE_LIMIT)
			.map(bucketItem -> itemReader.read(bucketItem.getItemId()))
			.map(Item::getImage)
			.toList();
	}

	private int getPageSize(final CursorPageParameters parameters) {
		int pageSize = parameters.size() == null ? DEFAULT_PAGING_SIZE : parameters.size();
		return pageSize;
	}

	public List<Long> getItemIds(final Long bucketId) {
		List<BucketItem> bucketItems = bucketItemRepository.findAllByBucketId(bucketId);

		return bucketItems.stream()
			.map(bucketItem -> bucketItem.getItemId())
			.toList();
	}

	public int countByMemberIdAndHobby(
		final Long memberId,
		final Hobby hobby
	) {
		return bucketRepository.countByHobbyAndMemberId(hobby, memberId);
	}
}
