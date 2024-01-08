package com.programmers.lime.domains.feed.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.BaseEntity;
import com.programmers.lime.domains.bucket.domain.BucketInfo;
import com.programmers.lime.domains.comment.domain.Comment;

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

	@OneToMany(mappedBy = "feed", cascade = CascadeType.REMOVE)
	private List<FeedLike> likes = new ArrayList<>();

	@OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
	private List<FeedItem> feedItems = new ArrayList<>();

	@OneToMany(mappedBy = "feed", cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();

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

	public String getFeedContent() {
		return content.getContent();
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

	public int getTotalPrice() {
		return this.feedItems.stream()
			.mapToInt(FeedItem::getItemPrice)
			.sum();
	}
}
