package com.programmers.lime.global.config.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.programmers.lime.global.config.security.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authenticationProvider;
	private final AccessDeniedHandler accessDeniedHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
		http
			.headers(headers -> headers
				.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable) // 추후 same origin으로 변경
			)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
			.authorizeHttpRequests(authorize ->
				authorize
					.requestMatchers("/swagger-ui/**").permitAll()
					.requestMatchers("/swagger*/**").permitAll()
					.requestMatchers("/v3/api-docs/**").permitAll()

					.requestMatchers("/api/hobbies")
					.permitAll()

					.requestMatchers(HttpMethod.PUT, "/api/favorites/items/move")
					.authenticated() // 찜 아이템 이동
					.requestMatchers(HttpMethod.PUT, "/api/favorites/folders/{folderId}")
					.authenticated() // 찜 폴더 이름 수정
					.requestMatchers(HttpMethod.POST, "/api/favorites/items")
					.authenticated() // 찜
					.requestMatchers(HttpMethod.POST, "/api/favorites/folders")
					.authenticated() // 찜 목록 폴더 생성
					.requestMatchers(HttpMethod.GET, "/api/favorites")
					.permitAll() // 찜 목록 조회
					.requestMatchers(HttpMethod.DELETE, "/api/favorites")
					.authenticated() // 찜 항목 제거

					.requestMatchers(HttpMethod.GET, "/api/votes")
					.permitAll()
					.requestMatchers(HttpMethod.GET, "/api/votes/{voteId}")
					.permitAll()
					.requestMatchers(HttpMethod.GET, "/api/votes/{nickname}/my")
					.permitAll()

					.requestMatchers("/api/{nickname}/buckets/**")
					.permitAll()

					.requestMatchers("/api/members/check/nickname")
					.permitAll()
					.requestMatchers("/api/members/mypage/{nickname}")
					.permitAll()
					.requestMatchers("/api/members/refresh")
					.permitAll()

					.requestMatchers("/ws-stomp/**")
					.permitAll()
					.requestMatchers(HttpMethod.GET, "/api/chats")
					.permitAll() // 채팅 목록 조회

					.requestMatchers("/api/chats/{chatRoomId}")
					.permitAll()

					.requestMatchers(HttpMethod.PUT, "/api/reviews/{reviewId}")
					.authenticated() // 아이템 리뷰 수정
					.requestMatchers(HttpMethod.DELETE, "/api/reviews/{reviewId}")
					.authenticated() // 아이템 리뷰 삭제
					.requestMatchers(HttpMethod.GET, "/api/reviews/")
					.permitAll() // 아이템 리뷰 목록 조회
					.requestMatchers(HttpMethod.POST, "/api/reviews/")
					.authenticated() // 아이템 리뷰 생성
					.requestMatchers(HttpMethod.POST, "/api/reviews/{reviewId}/like")
					.authenticated() // 아이템 리뷰 좋아요
					.requestMatchers(HttpMethod.DELETE, "/api/reviews/{reviewId}/like")
					.authenticated() // 아이템 리뷰 좋아요 취소

					.requestMatchers("/api/friendships/follower/**")
					.permitAll()
					.requestMatchers("/api/friendships/following/**")
					.permitAll()

					.requestMatchers(HttpMethod.GET, "/api/feeds/{feedId}")
					.permitAll()
					.requestMatchers(HttpMethod.GET, "/api/feeds")
					.permitAll()
					.requestMatchers(HttpMethod.GET, "/api/feeds/{feedId}/comments")
					.permitAll()

					.requestMatchers(HttpMethod.POST, "/api/items/enroll")
					.authenticated() // 아이템 등록
					.requestMatchers(HttpMethod.GET, "/api/items/{itemId}")
					.permitAll() // 아이템 상세 조회
					.requestMatchers(HttpMethod.GET, "/api/items/search")
					.permitAll() // 아이템 목록 조회
					.requestMatchers(HttpMethod.GET, "/api/items/ranking")
					.permitAll() // 아이템 랭킹 조회
					.requestMatchers(HttpMethod.GET, "/api/items/item-names")
					.permitAll() // 아이템 이름 목록 조회

					.requestMatchers("/api/{nickname}/inventories/**")
					.permitAll()

					.requestMatchers(HttpMethod.POST, "/api/chatrooms/{chatRoomId}/join")
					.permitAll() // 채팅방 참여
					.requestMatchers(HttpMethod.GET, "/api/chatrooms")
					.permitAll() // 채팅방 전체 조회
					.requestMatchers(HttpMethod.GET, "/api/chatrooms/{chatRoomId}/members/count")
					.permitAll() // 채팅방 인원수 조회
					.requestMatchers(HttpMethod.DELETE, "/api/chatrooms/{chatRoomId}/exit")
					.permitAll() // 채팅방 나가기

					.requestMatchers("/login").permitAll()
					.requestMatchers("/auth/kakao/callback/{code}").permitAll()
					.requestMatchers("/auth/kakao/**").permitAll()

					.requestMatchers("/join").permitAll()

					.anyRequest().hasRole("USER")
			)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling((exceptions) -> exceptions.accessDeniedHandler(accessDeniedHandler)
			);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setExposedHeaders(List.of("Authorization"));
		configuration.setAllowedOriginPatterns(List.of("*")); //자원 공유를 허락할 모든 경로
		configuration.setAllowedHeaders(List.of("*")); //요청 허용 헤더
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // http메소드 허용
		configuration.setAllowCredentials(true); //클라이언트에 응답에 쿠키 인증 헤더 포함하도록 한다.
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

}
