package com.programmers.lime.domains.item.domain;

import java.util.Objects;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "items")
public class Item extends BaseEntity {

	private static final int URL_MAX_SIZE = 1000;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "hobby", nullable = false)
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "price", nullable = false)
	private Integer price;

	@Column(name = "url", nullable = false, length = URL_MAX_SIZE)
	private String url;

	@Column(name = "image", nullable = false)
	private String image;

	@Builder
	public Item(
		final Hobby hobby,
		final String name,
		final Integer price,
		final String url,
		final String image
	) {
		this.hobby = Objects.requireNonNull(hobby);
		this.name = Objects.requireNonNull(name);
		this.price = Objects.requireNonNull(price);
		this.url = Objects.requireNonNull(url);
		this.image = Objects.requireNonNull(image);
	}
}
