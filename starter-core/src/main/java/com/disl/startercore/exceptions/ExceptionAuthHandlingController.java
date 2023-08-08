package com.disl.startercore.exceptions;

import com.disl.commons.payloads.Response;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

@Hidden
@ControllerAdvice
public class ExceptionAuthHandlingController {
	@ResponseBody
	@ExceptionHandler(value = AuthenticationException.class)
	public ResponseEntity<Object> handleAuthenticationExceptions(AuthenticationException ex,
			HttpServletResponse response) {
		return new ResponseEntity<>(new Response(HttpStatus.FORBIDDEN, false, "Invalid email/password provided.", ex.getLocalizedMessage()),
				HttpStatus.UNAUTHORIZED);
	}

	@ResponseBody
	@ExceptionHandler(value = NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleNoHandlerFound(NoHandlerFoundException ex, WebRequest request) {
		return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND, false, "Application cannot reach to server",
				ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
	}

}
