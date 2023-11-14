package com.programmers.bucketback.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
		log.error("UnExpected Exception", e);
		final ErrorResponse response = ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
		final MethodArgumentNotValidException e) {
		log.error("MethodArgumentNotValidException Exception", e);
		final BindingResult bindingResult = e.getBindingResult();
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_REQUEST, bindingResult);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<ErrorResponse> handleBadCredentialsException(final BadCredentialsException e) {
		log.error("BadCredentialsException", e);
		final ErrorResponse response = ErrorResponse.from(ErrorCode.MEMBER_LOGIN_FAIL);
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(MessagingException.class)
	protected ResponseEntity<ErrorResponse> handleMessagingException(final MessagingException e) {
		log.error("MessagingException", e);
		final ErrorResponse response = ErrorResponse.from(ErrorCode.MAIL_SEND_FAIL);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
		log.error("MissingServletRequestParameterException", e);
		final ErrorResponse response = ErrorResponse.from(ErrorCode.MISSING_PARAMETER);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
		log.error("BusinessException", e);
		final ErrorResponse response = ErrorResponse.from(e.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(final EntityNotFoundException e) {
		log.error("EntityNotFoundException", e);
		final ErrorResponse response = ErrorResponse.from(e.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
