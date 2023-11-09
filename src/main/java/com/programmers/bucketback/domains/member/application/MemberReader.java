package com.programmers.bucketback.domains.member.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.bucket.application.BucketReader;
import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketItem;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
import com.programmers.bucketback.domains.inventory.domain.InventoryItem;
import com.programmers.bucketback.domains.inventory.repository.InventoryRepository;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.member.application.vo.BucketProfile;
import com.programmers.bucketback.domains.member.application.vo.MemberProfile;
import com.programmers.bucketback.domains.member.application.vo.MyPage;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.repository.MemberRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberReader {

	private final MemberRepository memberRepository;
	private final InventoryRepository inventoryRepository;
	private final BucketReader bucketReader;

	public Member read(final String email) {
		return memberRepository.findByLoginInfoEmail(email)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_LOGIN_FAIL));
	}

	public Member read() {
		final Long memberId = MemberUtils.getCurrentMemberId();

		return memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public Member readByNickname(final String nickname) {
		return memberRepository.findByNickname(nickname)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public MyPage readMyPage(final String nickname) {
		Member member = readByNickname(nickname);
		MemberProfile memberProfile = MemberProfile.from(member);

		List<Bucket> buckets = bucketReader.readByMemberId(member.getId());
		List<BucketProfile> bucketProfiles = selectBucketProfile(buckets);

		List<Inventory> inventories = inventoryRepository.findByMemberId(member.getId());
		List<InventoryProfile> inventoryProfiles = selectInventoryProfile(inventories);

		return new MyPage(memberProfile, bucketProfiles, inventoryProfiles);
	}

	private List<BucketProfile> selectBucketProfile(final List<Bucket> buckets) {
		List<Bucket> selectedBuckets = selectBucketsByHobby(buckets);
		return selectedBuckets.stream()
			.map(bucket -> {
				List<String> itemImages = extractBucketItemImages(bucket);
				return BucketProfile.of(bucket, itemImages);
			})
			.toList();
	}

	private List<Bucket> selectBucketsByHobby(final List<Bucket> selectedBuckets) {
		return selectedBuckets.stream()
			.collect(Collectors.groupingBy(Bucket::getHobby))
			.values()
			.stream()
			.map(group -> group.get(0))
			.limit(3)
			.toList();
	}

	private List<String> extractBucketItemImages(final Bucket bucket) {
		return bucket.getBucketItems().stream()
			.limit(4)
			.map(BucketItem::getItem)
			.map(Item::getImage)
			.toList();
	}

	private List<InventoryProfile> selectInventoryProfile(final List<Inventory> inventories) {
		List<Inventory> selectedInventories = inventories.stream()
			.limit(3)
			.toList();
		return selectedInventories.stream()
			.map(inventory -> {
				List<String> itemImages = extractInventoryItemImages(inventory);
				return InventoryProfile.of(inventory, itemImages);
			})
			.toList();
	}

	private List<String> extractInventoryItemImages(final Inventory inventory) {
		return inventory.getInventoryItems().stream()
			.limit(4)
			.map(InventoryItem::getItem)
			.map(Item::getImage)
			.toList();
	}

}
