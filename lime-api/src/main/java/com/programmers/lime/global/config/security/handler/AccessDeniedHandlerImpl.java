package com.programmers.lime.global.config.security.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
	@Override
	public void handle(
		final HttpServletRequest request,
		final HttpServletResponse response,
		final AccessDeniedException accessDeniedException
	) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.sendRedirect("/join");
	}
}
