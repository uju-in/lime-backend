package com.programmers.lime.domains.auth.api.dto;

import static com.programmers.lime.domains.member.domain.vo.SocialType.*;

import java.util.UUID;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.vo.Role;
import com.programmers.lime.domains.member.domain.vo.SocialInfo;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoMemberResponse(
	Long id, //카카오 소셜 id

	KakaoAccount kakaoAccount

) {
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public record KakaoAccount(
		String email,
		Profile profile
	) {
		@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
		public record Profile(
			String profileImageUrl
		) {
		}
	}

	public Member toEntity(){
		SocialInfo socialInfo = SocialInfo.builder()
			.socialId(this.id)
			.email(this.kakaoAccount.email)
			.profileImage(this.kakaoAccount.profile.profileImageUrl)
			.role(Role.GUEST)
			.socialType(KAKAO)
			.build();

		long timestamp = System.currentTimeMillis();
		String randomString = String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 8);
		String randomNickname = timestamp + randomString;

		return new Member(socialInfo, randomNickname);
	}
}