package com.programmers.lime.global.config.security.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.global.config.security.MemberSecurity;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	public static final int TOKEN_BEGIN_INDEX = 7;

	private final JwtService jwtService;
	private final HandlerExceptionResolver handlerExceptionResolver;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
		@NonNull final HttpServletRequest request,
		@NonNull final HttpServletResponse response,
		@NonNull final FilterChain filterChain
	) throws ServletException, IOException {
		final String requestUri = request.getRequestURI();
		final String authHeader = request.getHeader("Authorization");
		final String jwt;

		if (requestUri.equals("/api/members/refresh") || authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(TOKEN_BEGIN_INDEX);

		try {
			if (SecurityContextHolder.getContext().getAuthentication() == null && jwtService.isAccessTokenValid(jwt)) {
				final String memberId = jwtService.extractUsername(jwt);
				final UserDetails principal = makePrincipal(memberId);

				// if(needsProfileUpdate(principal)){
				// 	// response.setHeader("Location","http://localhost:8080/join");
				// 	// response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				// 	// response.sendRedirect("http://localhost:8080/join");
				//
				// }

				final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					principal,
					null,
					principal.getAuthorities()
				);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}

			filterChain.doFilter(request, response);

		} catch (JwtException e) {
			handlerExceptionResolver.resolveException(request, response, null, e);
		}
	}

	// private static boolean needsProfileUpdate(final UserDetails principal) {
	// 	Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
	// 	for(GrantedAuthority authority : authorities){
	// 		String role = authority.getAuthority();
	// 		if(role.equals("ROLE_GUEST")){
	// 			return true;
	// 		}
	// 	}
	// 	return false;
	// }

	private UserDetails makePrincipal(final String memberId) {
		return userDetailsService.loadUserByUsername(memberId);
	}

	// private static UserDetails makePrincipal(final String memberId) {
	// 	return new User(memberId, "", List.of(new SimpleGrantedAuthority("ROLE_USER")));
	// }
}
