package com.programmers.lime.domains.feed.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.domains.bucket.domain.Bucket;
import com.programmers.lime.domains.bucket.domain.BucketBuilder;
import com.programmers.lime.domains.feed.FeedBuilder;
import com.programmers.lime.domains.feed.domain.Feed;
import com.programmers.lime.domains.feed.domain.FeedItem;
import com.programmers.lime.domains.feed.model.FeedDetail;
import com.programmers.lime.domains.feed.model.FeedInfo;
import com.programmers.lime.domains.feed.model.FeedItemInfo;
import com.programmers.lime.domains.feed.repository.FeedLikeRepository;
import com.programmers.lime.domains.feed.repository.FeedRepository;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.MemberBuilder;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.domains.member.model.MemberInfo;

@ExtendWith(MockitoExtension.class)
class FeedReaderTest {

	@Mock
	private FeedRepository feedRepository;

	@Mock
	private FeedLikeRepository feedLikeRepository;

	@Mock
	private MemberReader memberReader;

	@InjectMocks
	private FeedReader feedReader;

	@Test
	@DisplayName("피드 상세 조회에 성공한다.")
	void successReadFeedDetail() {
		//given
		Long memberId = 1L;
		Member member = MemberBuilder.build(1L);
		Feed feed = FeedBuilder.build();
		Long feedId = 1L;

		MemberInfo memberInfo = new MemberInfo(member.getId(),
			member.getNickname(),
			member.getProfileImage(),
			member.getLevel()
		);

		Bucket bucket = BucketBuilder.build();
		List<FeedItem> feedItems = bucket.getBucketItems().stream()
			.map(bucketItem -> new FeedItem(bucketItem.getItem()))
			.toList();
		feedItems.forEach(feed::addFeedItem);

		FeedInfo feedInfo = FeedInfo.builder()
			.id(feed.getId())
			.hobby(feed.getHobby().getHobbyValue())
			.content(feed.getFeedContent())
			.bucketName(feed.getName())
			.bucketBudget(feed.getBudget())
			.totalPrice(feed.getTotalPrice())
			.createdAt(feed.getCreatedAt())
			.hasAdoptedComment(false)
			.isLiked(true)
			.likeCount(feed.getLikes().size())
			.build();

		List<FeedItemInfo> feedItemInfos = feedItems.stream()
			.map(FeedItemInfo::from)
			.toList();

		given(memberReader.read(anyLong())).willReturn(member);
		given(feedLikeRepository.existsByMemberIdAndFeed(anyLong(), any())).willReturn(true);
		given(feedRepository.findById(anyLong())).willReturn(Optional.ofNullable(feed));

		//when
		FeedDetail feedDetail = feedReader.readDetail(feedId, memberId);

		//then
		assertThat(feedDetail.feedInfo()).usingRecursiveComparison().isEqualTo(feedInfo);
		assertThat(feedDetail.memberInfo()).usingRecursiveComparison().isEqualTo(memberInfo);
		assertThat(feedDetail.feedItems()).usingRecursiveComparison().isEqualTo(feedItemInfos);
	}
}