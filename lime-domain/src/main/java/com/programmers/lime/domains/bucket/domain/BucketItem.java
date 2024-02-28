package com.programmers.lime.domains.bucket.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "bucket_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BucketItem{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "item_id")
	private Long itemId;

	@Column(name = "bucket_id")
	private Long bucketId;

	public BucketItem(
		final Long itemId,
		final Long bucketId
	){
		this.itemId = itemId;
		this.bucketId = bucketId;
	}

	public BucketItem(
		final Long id,
		final Long itemId,
		final Long bucketId
	){
		this.id = id;
		this.itemId = itemId;
		this.bucketId = bucketId;
	}
}
