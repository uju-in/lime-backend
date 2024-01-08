package com.programmers.lime.domains.sse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sse")
public class SseController {

	private final SseService sseService;

	@GetMapping(value = "/subscribe", produces = "text/event-stream;charset=utf-8")
	public ResponseEntity<SseEmitter> subscribe(HttpServletResponse response) {
		response.setHeader(HttpHeaders.CONNECTION, "keep-alive");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "text/event-stream;charset=utf-8");
		response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-transform");

		return ResponseEntity.ok(sseService.subscribe());
	}
}
