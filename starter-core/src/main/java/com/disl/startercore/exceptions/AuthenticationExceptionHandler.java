package com.disl.startercore.exceptions;

import com.disl.commons.payloads.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.Serializable;
@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint, Serializable {

	@Override
	public void commence(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		Response errorResponse = new Response(HttpStatus.FORBIDDEN, false, authException.getLocalizedMessage(), null);

		ObjectMapper mapper = new ObjectMapper();
		String responseMsg = mapper.writeValueAsString(errorResponse);
		response.getWriter().write(responseMsg);
		response.setStatus(403);
	}
}
