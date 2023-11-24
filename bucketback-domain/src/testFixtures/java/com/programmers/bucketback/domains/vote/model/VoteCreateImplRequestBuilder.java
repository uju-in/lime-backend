package com.programmers.bucketback.domains.vote.model;

import com.programmers.bucketback.common.model.Hobby;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoteCreateImplRequestBuilder {
	public static VoteCreateImplRequest build() {
		return VoteCreateImplRequest.builder()
			.hobby(Hobby.BASKETBALL)
			.content("농구공 사려는데 뭐가 더 좋음?")
			.item1Id(1L)
			.item2Id(2L)
			.build();
	}
}
