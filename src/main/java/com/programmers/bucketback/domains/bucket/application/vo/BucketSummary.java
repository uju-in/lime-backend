package com.programmers.bucketback.domains.bucket.application.vo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BucketSummary{
	private Long bucketId;
	private String bucketName;
	private Integer bucketBudget;
	private LocalDateTime createdAt;
	private List<ItemImage> itemImages;
}
