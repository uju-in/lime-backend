package com.programmers.bucketback.domains.item.domain;

import com.programmers.bucketback.domains.common.BaseEntity;
import com.programmers.bucketback.domains.common.Hobby;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "items")
public class Item extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "hobby")
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "brand")
	private String brand;

	@NotNull
	@Column(name = "price")
	private Integer price;

	@NotNull
	@Column(name = "url")
	private String url;

	@NotNull
	@Column(name = "image")
	private String image;

	@Builder
	public Item(Hobby hobby,
							String name,
							String brand,
							Integer price,
							String url,
							String image
							){
		this.hobby = hobby;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.url = url;
		this.image = image;
	}

}
