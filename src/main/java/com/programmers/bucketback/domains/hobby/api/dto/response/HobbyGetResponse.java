package com.programmers.bucketback.domains.hobby.api.dto.response;

import java.util.List;

public record HobbyGetResponse(
	List<HobbyItem> hobbies
) {
}
