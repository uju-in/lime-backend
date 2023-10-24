package com.programmers.bucketback.domains.feed.domain;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "feeds")
public class Feed extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@NotNull
	@Column(name="member_id")
	private Long memberId;

	@NotNull
	@Column(name="hobby")
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@NotNull
	@Column(name="content")
	private String content;

	@NotNull
	@Column(name="bucket_name")
	private String bucketName;

	@NotNull
	@Column(name="bucket_budget")
	private String bucketBudget;
}
