package com.programmers.bucketback.domains.feed.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
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
@Table(name = "feeds")
public class Feed extends BaseEntity {

	@JsonIgnore
	@OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
	private final List<FeedItem> feedItems = new ArrayList<>();
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
	@NotNull
	@Column(name = "message")
	private String message;
	@Embedded
	private BucketInfo bucketInfo;

	@Builder
	public Feed(
		final Long memberId,
		final Hobby hobby,
		final String message,
		final String bucketName,
		final Integer bucketBudget
	) {
		this.memberId = memberId;
		this.hobby = hobby;
		this.message = message;
		this.bucketInfo = new BucketInfo(bucketName, bucketBudget);
	}

	public void addFeedItem(final FeedItem feedItem) {
		feedItems.add(feedItem);
		feedItem.changeFeed(this);
	}

	public void modifyFeed(final String message) {
		this.message = message;
	}
}
