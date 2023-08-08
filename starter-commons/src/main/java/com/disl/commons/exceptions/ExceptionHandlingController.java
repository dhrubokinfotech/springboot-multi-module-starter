package com.disl.commons.exceptions;

import com.disl.commons.payloads.Response;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.Optional;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Hidden
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String error = "Invalid input.";
		return buildResponseEntity((new Response(HttpStatus.valueOf(status.value()), false, error, ex.getLocalizedMessage())));
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		// TODO Auto-generated method stub
		return buildResponseEntity((new Response(HttpStatus.valueOf(status.value()), false, "Invalid Input", ex.getLocalizedMessage())));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		// TODO Auto-generated method stub
		return buildResponseEntity(
				(new Response(HttpStatus.valueOf(status.value()), false, "Invalid File Type provided.", ex.getLocalizedMessage())));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		// TODO Auto-generated method stub
		return buildResponseEntity(
				(new Response(HttpStatus.valueOf(status.value()), false, "Server Error. Write Failed", ex.getLocalizedMessage())));
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		// TODO Auto-generated method stub
		return buildResponseEntity((new Response(HttpStatus.valueOf(status.value()), false, "Invalid type of request.", ex.getLocalizedMessage())));
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

		// TODO Auto-generated method stub
		return buildResponseEntity((new Response(HttpStatus.valueOf(statusCode.value()), false, "Server Error Occurred.", ex.getLocalizedMessage())));
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		// TODO Auto-generated method stub
		return buildResponseEntity((new Response(HttpStatus.valueOf(status.value()), false, "Request Failed. Invalid Request. Please Try Again.",
				ex.getLocalizedMessage())));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		// TODO Auto-generated method stub
		return buildResponseEntity((new Response(HttpStatus.valueOf(status.value()), false, "Request Failed. Invalid Request. Please Try Again.",
				ex.getLocalizedMessage())));
	}

	@ResponseBody
	@ExceptionHandler(ResponseException.class)
	public ResponseEntity<Object> handleResponseException(ResponseException ex) {
		Object payload = ex.getPayload();
		HttpStatus httpStatus = ex.getHttpStatus();

		Response errorResponse = new Response(httpStatus != null ? httpStatus :  HttpStatus.BAD_REQUEST, false, ex.getMessage(), payload);
		return buildResponseEntity(errorResponse);
	}

	@ResponseBody
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<Object> handleSQLException(SQLException ex) {
		return buildResponseEntity((new Response(HttpStatus.BAD_REQUEST, false, "Request Failed. Invalid Request. Please Try Again.",
				ex.getLocalizedMessage())));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Response errorResponse = new Response(HttpStatus.valueOf(status.value()), false, "Invalid request body.", null);
		Optional<ObjectError> objectError = ex.getBindingResult().getAllErrors().stream().findFirst();

		if(objectError.isPresent()) {
			ObjectError error = objectError.get();
			errorResponse.setMessage(((FieldError) error).getField() + " field " + error.getDefaultMessage());

			return buildResponseEntity(errorResponse);
		}

		return buildResponseEntity(errorResponse);
	}

	private ResponseEntity<Object> buildResponseEntity(Response apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
