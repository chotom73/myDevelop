package com.packtpub.springsecurity.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class SignedUsernamePasswordAuthenticationProvider extends DaoAuthenticationProvider {
	
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (SignedUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		super.additionalAuthenticationChecks(userDetails, authentication);
		SignedUsernamePasswordAuthenticationToken signedToken = (SignedUsernamePasswordAuthenticationToken) authentication;
		
		if (signedToken.getRequestSingnature() == null) {
			throw new BadCredentialsException(messages.getMessage(
                    "SignedUsernamePasswordAuthenticationProvider.missingSignature", "Missing request signature"));
		}
		
		// 예상한 시그너처를 계산한다.
		if (!signedToken.getRequestSingnature().equals(calculateExpectedSignature(signedToken))) {
			throw new BadCredentialsException(messages.getMessage(
                    "SignedUsernamePasswordAuthenticationProvider.badSignature", "Invalid request signature"));
		}
	}

	private Object calculateExpectedSignature(SignedUsernamePasswordAuthenticationToken signedToken) {
		// TODO Auto-generated method stub
		return signedToken.getPrincipal() + "|+|" + signedToken.getCredentials();
	}

	
}
