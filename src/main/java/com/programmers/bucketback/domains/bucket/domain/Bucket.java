package com.programmers.bucketback.domains.bucket.domain;

import java.util.ArrayList;
import java.util.List;

import com.programmers.bucketback.domains.common.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

	@NotNull
	@Column(name = "member_id")
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

	public void modifyBucket(
		final BucketInfo bucketInfo,
		final Long memberId
	) {
		this.memberId = memberId;
		this.bucketInfo = bucketInfo;
	}

	public void removeBucketItems() {
		this.bucketItems.clear();
	}
}
