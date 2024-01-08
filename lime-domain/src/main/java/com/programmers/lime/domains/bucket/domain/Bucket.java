package com.programmers.lime.domains.bucket.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "buckets")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bucket extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Embedded
	private BucketInfo bucketInfo;

	@OneToMany(mappedBy = "bucket", cascade = CascadeType.ALL)
	private List<BucketItem> bucketItems = new ArrayList<>();

	public Bucket(
		final BucketInfo bucketInfo,
		final Long memberId
	) {
		this.memberId = memberId;
		this.bucketInfo = bucketInfo;
	}

	public void addBucketItem(final BucketItem bucketItem) {
		bucketItems.add(bucketItem);
		bucketItem.changeBucket(this);
	}

	public String getName() {
		return bucketInfo.getName();
	}

	public Hobby getHobby() {
		return bucketInfo.getHobby();
	}

	public Integer getBudget() {
		return bucketInfo.getBudget();
	}

	public void modifyBucket(
		final BucketInfo bucketInfo,
		final Long memberId
	) {
		this.memberId = Objects.requireNonNull(memberId);
		this.bucketInfo = bucketInfo;
	}

	public void removeBucketItems() {
		this.bucketItems.clear();
	}
}
