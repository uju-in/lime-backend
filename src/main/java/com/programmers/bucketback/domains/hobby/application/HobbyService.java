package com.programmers.bucketback.domains.hobby.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.hobby.api.dto.response.HobbyGetResponse;

@Service
public class HobbyService {

	public HobbyGetResponse getHobbies() {
		List<Hobby> hobbies = List.of(Hobby.values());
		return new HobbyGetResponse(
			hobbies
		);
	}
}
