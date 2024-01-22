package com.programmers.lime.domains.item.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.item.model.MemberItemObjectInfo;

public record MemberItemObjectGetResponse(
	int totalCount,
	List<MemberItemObjectInfo> objects
){
}
