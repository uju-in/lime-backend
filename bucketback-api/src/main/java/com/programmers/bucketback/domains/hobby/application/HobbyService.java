package com.programmers.bucketback.domains.hobby.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.Hobby;
import com.programmers.bucketback.domains.hobby.api.dto.response.HobbyGetResponse;
import com.programmers.bucketback.domains.hobby.api.dto.response.HobbyItem;

@Service
public class HobbyService {

	public HobbyGetResponse getHobbies() {
		List<Hobby> hobbies = List.of(Hobby.values());

		List<HobbyItem> hobbyItems = hobbies.stream().map(
			hobby -> new HobbyItem(
				hobby.getName(),
				hobby.getHobbyValue()
			)
		).toList();

		return new HobbyGetResponse(
			hobbyItems
		);
	}
}
