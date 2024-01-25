package com.programmers.lime.domains.review.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne
	@JoinColumn(name = "review_id", nullable = false)
	private Review review;

	@Column(name = "image_url", nullable = false)
	private String imageUrl;

	public ReviewImage(
		final Review review,
		final String imageUrl
	) {
		this.review = Objects.requireNonNull(review);
		this.imageUrl = Objects.requireNonNull(imageUrl);
	}
}
