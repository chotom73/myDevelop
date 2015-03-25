package com.packtpub.springsecurity.security;

import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

public class CustomWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {
	@Override
	protected StandardEvaluationContext createEvaluationContextInternal(
			Authentication authentication, FilterInvocation invocation) {
		// TODO Auto-generated method stub
		
		StandardEvaluationContext ctx = (StandardEvaluationContext) super.createEvaluationContextInternal(authentication, invocation);
		SecurityExpressionRoot root = new CustomWebSecurityExpressionRoot(authentication, invocation);
		ctx.setRootObject(root);
		
		return ctx;
	}
}
