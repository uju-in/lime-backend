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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.global.config.security.auth.handler.OAuth2LoginFailureHandler;
import com.programmers.lime.global.config.security.auth.handler.OAuth2LoginSuccessHandler;
import com.programmers.lime.global.config.security.jwt.JwtAuthenticationFilter;
import com.programmers.lime.global.config.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final OAuth2UserService oAuth2UserService; //추가
	private final JwtService jwtService; // 추가
	private final MemberReader memberReader;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
			.authorizeHttpRequests(authorize ->
				authorize
					.requestMatchers("/swagger-ui/**").permitAll()
					.requestMatchers("/swagger*/**").permitAll()
					.requestMatchers("/v3/api-docs/**").permitAll()
					.requestMatchers("/").permitAll()

					.requestMatchers("/api/members/signup").permitAll()
					.requestMatchers("/api/members/login").permitAll()
					.requestMatchers("/api/members/check/nickname").permitAll()
					.requestMatchers("/api/members/check/email").permitAll()
					.requestMatchers("/api/members/mypage/{nickname}").permitAll()
					.requestMatchers("/api/members/refresh").permitAll()

					.requestMatchers("/api/friendships/follower/**").permitAll()
					.requestMatchers("/api/friendships/following/**").permitAll()

					.requestMatchers(HttpMethod.GET, "/api/votes").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/votes/{voteId}").permitAll()

					.requestMatchers(HttpMethod.GET, "/api/items/{itemId}/reviews").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/items/{itemId}").permitAll()
					.requestMatchers("/api/items/search").permitAll()
					.requestMatchers("/api/items/item-names").permitAll()

					.requestMatchers(HttpMethod.GET, "/api/feeds/{feedId}").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/feeds").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/feeds/{feedId}/comments").permitAll()

					.requestMatchers("/api/{nickname}/inventories/**").permitAll()
					.requestMatchers("/api/{nickname}/buckets/**").permitAll()

					.requestMatchers("/api/hobbies").permitAll()
					.requestMatchers("/login").permitAll()
					.requestMatchers("/actuator/**").permitAll()
					.anyRequest().authenticated()
			)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		http.oauth2Login(oauth2Configurer -> oauth2Configurer
			.successHandler(successHandler())
			.failureHandler(failureHandler())
			.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuth2UserService)));

		return http.build();
	}

	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new OAuth2LoginFailureHandler();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new OAuth2LoginSuccessHandler(jwtService, memberReader);
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
