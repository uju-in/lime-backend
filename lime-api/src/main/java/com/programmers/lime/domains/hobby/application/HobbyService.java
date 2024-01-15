package com.programmers.lime.domains.hobby.application;

import org.springframework.stereotype.Service;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.hobby.api.dto.response.HobbyGetResponse;

@Service
public class HobbyService {

	public HobbyGetResponse getHobbies() {
		return new HobbyGetResponse(Hobby.getHobbies());
	}
}
