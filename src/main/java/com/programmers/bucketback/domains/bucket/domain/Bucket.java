package com.programmers.bucketback.domains.bucket.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.programmers.bucketback.domains.common.BaseEntity;
import com.programmers.bucketback.domains.common.Hobby;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "buckets")
public class Bucket extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "member_id")
	private Long memberId;

	@NotNull
	@Column(name = "hobby")
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@Embedded
	private BucketInfo bucketInfo;

	@JsonIgnore
	@OneToMany(mappedBy = "bucket", cascade = CascadeType.ALL)
	private List<BucketItem> bucketItems = new ArrayList<>();

	@Builder
	public Bucket(
		final Hobby hobby,
		final Long memberId,
		final String name,
		final Integer budget
	){
		this.hobby = hobby;
		this.memberId = memberId;
		this.bucketInfo = new BucketInfo(name, budget);
	}

	public void addBucketItem(final BucketItem bucketItem) {
		bucketItems.add(bucketItem);
		bucketItem.changeBucket(this);
	}

	public void modifyBucket(
		final Hobby hobby,
		final Long memberId,
		final String name,
		final Integer budget
	) {
		this.hobby = hobby;
		this.memberId= memberId;
		this.bucketInfo = new BucketInfo(name,budget);
	}

	public void removeBucketItems(){
		this.bucketItems.clear();
	}
}
