package com.programmers.bucketback.domains.feed.domain;

import com.programmers.bucketback.domains.item.domain.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "feed_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feed_id")
	private Feed feed;

	public FeedItem(final Item item) {
		this.item = item;
	}

	public int getItemPrice() {
		return this.item.getPrice();
	}

	public void changeFeed(final Feed feed) {
		this.feed = feed;
	}
}
