package com.programmers.lime.domains.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "review_images")
public class ReviewImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "review_id", nullable = false)
	private Long reviewId;

	@Column(name = "image_url", nullable = false)
	private String imageUrl;

	public ReviewImage(
		final Long reviewId,
		final String imageUrl
	) {
		this.reviewId = reviewId;
		this.imageUrl = imageUrl;
	}
}
