package com.programmers.lime.global.config.security.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
		final AuthenticationException authException) throws IOException, ServletException {
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.sendRedirect("/join");
	}
}
