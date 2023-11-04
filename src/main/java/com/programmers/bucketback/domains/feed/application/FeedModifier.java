package com.programmers.bucketback.domains.feed.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.feed.repository.FeedRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedModifier {

	private final FeedRepository feedRepository;
}
