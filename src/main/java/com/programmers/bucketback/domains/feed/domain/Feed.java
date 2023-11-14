package com.programmers.bucketback.domains.feed.domain;

import java.util.ArrayList;
import java.util.List;

import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
import com.programmers.bucketback.domains.comment.domain.Comment;
import com.programmers.bucketback.domains.common.BaseEntity;
import com.programmers.bucketback.domains.common.Hobby;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
@Entity
@Table(name = "feeds")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseEntity {

	@OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
	private final List<FeedItem> feedItems = new ArrayList<>();
	@OneToMany(mappedBy = "feed", cascade = CascadeType.REMOVE)
	private final List<Comment> comments = new ArrayList<>();
	@OneToMany(mappedBy = "feed", cascade = CascadeType.REMOVE)
	private final List<FeedLike> likes = new ArrayList<>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@NotNull
	@Column(name = "member_id")
	private Long memberId;
	@NotNull

	@Column(name = "message")
	private String message;
	@Embedded
	private BucketInfo bucketInfo;
  
	@OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
	private List<FeedItem> feedItems = new ArrayList<>();

	@OneToMany(mappedBy = "feed", cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();
	
  @OneToMany(mappedBy = "feed", cascade = CascadeType.REMOVE)
	private final List<FeedLike> likes = new ArrayList<>();

	@Builder
	public Feed(
		final Long memberId,
		final Hobby hobby,
		final String message,
		final String bucketName,
		final Integer bucketBudget
	) {
		this.memberId = memberId;
		this.message = message;
		this.bucketInfo = new BucketInfo(hobby, bucketName, bucketBudget);
	}

	public void addFeedItem(final FeedItem feedItem) {
		feedItems.add(feedItem);
		feedItem.changeFeed(this);
	}

	public void modifyFeed(final String message) {
		this.message = message;
	}

	public boolean isOwner(final Long memberId) {
		return this.memberId.equals(memberId);
	}

	public boolean hasAdoptedComment() {
		return this.comments.stream()
			.anyMatch(Comment::isAdoption);
	}
}
