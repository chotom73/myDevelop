package com.packtpub.springsecurity.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SignedUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private String requestSingnature;
	private static final long serialVersionUID = 6381775082307551389L;
	
	public SignedUsernamePasswordAuthenticationToken(String principal, String credentials, String signature) {
		super(principal, credentials);
		this.requestSingnature = signature;
	}
	
	public String getRequestSingnature() {
		return requestSingnature;
	}

	public void setRequestSingnature(String requestSingnature) {
		this.requestSingnature = requestSingnature;
	}
	


}
