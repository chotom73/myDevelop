package com.packtpub.springsecurity.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class RequestHeaderProcessingFIlter extends AbstractAuthenticationProcessingFilter {
	
	private String usernameHeader = "j_username";
	private String passwordHeader = "j_password";
	private String signatureHeader = "j_signature";

	protected RequestHeaderProcessingFIlter() {
		super("/j_spring_security_filter");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		// TODO Auto-generated method stub
		String username = request.getHeader(usernameHeader);
		String password = request.getHeader(passwordHeader);
		String signature = request.getHeader(signatureHeader);
		
		SignedUsernamePasswordAuthenticationToken authRequest = new SignedUsernamePasswordAuthenticationToken(username, password, signature);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

}
