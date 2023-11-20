package com.programmers.bucketback.domains.feed.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.BaseEntity;
import com.programmers.bucketback.domains.bucket.domain.BucketInfo;
import com.programmers.bucketback.domains.comment.domain.Comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
	@Column(name = "member_id", nullable = false)
	private Long memberId;
	@Embedded
	private FeedContent content;
	@Embedded
	private BucketInfo bucketInfo;

	@Builder
	public Feed(
		final Long memberId,
		final Hobby hobby,
		final String content,
		final String bucketName,
		final Integer bucketBudget
	) {
		this.memberId = Objects.requireNonNull(memberId);
		this.content = new FeedContent(content);
		this.bucketInfo = new BucketInfo(hobby, bucketName, bucketBudget);
	}

	public String getName() {
		return bucketInfo.getName();
	}

	public Hobby getHobby() {
		return bucketInfo.getHobby();
	}

	public Integer getBudget() {
		return bucketInfo.getBudget();
	}

	public void addFeedItem(final FeedItem feedItem) {
		feedItems.add(feedItem);
		feedItem.changeFeed(this);
	}

	public void modifyFeed(final String content) {
		this.content = new FeedContent(content);
	}

	public boolean isOwner(final Long memberId) {
		return this.memberId.equals(memberId);
	}

	public boolean hasAdoptedComment() {
		return this.comments.stream()
			.anyMatch(Comment::isAdoption);
	}
}
