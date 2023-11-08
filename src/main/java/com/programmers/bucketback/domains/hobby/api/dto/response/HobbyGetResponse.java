package com.programmers.bucketback.domains.hobby.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.common.Hobby;

public record HobbyGetResponse(
	List<Hobby> hobbies
) {
}
