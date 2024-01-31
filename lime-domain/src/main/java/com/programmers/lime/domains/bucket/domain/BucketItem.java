package com.programmers.lime.domains.bucket.domain;

import com.programmers.lime.domains.BaseEntity;
import com.programmers.lime.domains.item.domain.Item;

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
public class BucketItem extends BaseEntity {

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
}
