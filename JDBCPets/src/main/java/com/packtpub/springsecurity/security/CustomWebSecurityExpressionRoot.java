package com.packtpub.springsecurity.security;

import java.util.Calendar;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

public class CustomWebSecurityExpressionRoot extends WebSecurityExpressionRoot {

	public CustomWebSecurityExpressionRoot(Authentication a, FilterInvocation fi) {
		super(a, fi);
		// TODO Auto-generated constructor stub
	}
	
	private boolean isEventMinute() {
		// TODO Auto-generated method stub
		return (Calendar.getInstance().get(Calendar.MINUTE) % 2) == 0;
	}
}
