package com.programmers.bucketback.domains.bucket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.programmers.bucketback.domains.item.domain.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "bucket_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BucketItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bucket_id")
	private Bucket bucket;

	public BucketItem(final Item item){
		this.item = item;
	}

	public void changeBucket(final Bucket bucket) {
		this.bucket = bucket;
	}
}
