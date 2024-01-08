package com.programmers.lime.domains.hobby.api.dto.response;

import java.util.List;

public record HobbyGetResponse(
	List<HobbyItem> hobbies
) {
}
