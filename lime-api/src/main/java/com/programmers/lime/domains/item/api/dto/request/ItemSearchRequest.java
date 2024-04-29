package com.programmers.lime.domains.item.api.dto.request;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;

public record ItemSearchRequest (

	@Schema(description = "조회 할 아이템 이름의 일부", example = "농구")
	@Length(max = 100)
	String keyword,

	@Schema(description = "아이템 검색 조건", example = "REVIEW_COUNT_DESC")
	@Length(max = 50)
	String itemSortCondition,

	@Schema(description = "취미 이름", example = "농구")
	@Length(max = 20)
	String hobbyName
){
}
