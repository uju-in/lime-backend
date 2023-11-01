package com.programmers.bucketback.domains.review.domain;

import com.programmers.bucketback.domains.item.domain.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "items_id")
	private Item item;

	@NotNull
	@Column(name = "members_id")
	private Long memberId;

	@Column(name = "content")
	private String content;

	@NotNull
	@Column(name = "rating")
	private Integer rating;

	@Builder
	public Review(
		@NotNull final Item item,
		@NotNull final Long memberId,
		@NotNull final String content,
		@NotNull final Integer rating
	) {
		this.item = item;
		this.memberId = memberId;
		this.content = content;
		this.rating = rating;
	}
}
