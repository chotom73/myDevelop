package com.packtpub.springsecurity.security;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class IPRoleAuthenticationFilter extends OncePerRequestFilter {
	
	private String targetRole;
	public String getTargetRole() {
		return targetRole;
	}

	public void setTargetRole(String targetRole) {
		this.targetRole = targetRole;
	}

	public List<String> getAllowedIPAddress() {
		return allowedIPAddress;
	}

	public void setAllowedIPAddress(List<String> allowedIPAddress) {
		this.allowedIPAddress = allowedIPAddress;
	}

	private List<String> allowedIPAddress;

	@Override
	protected void doFilterInternal(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null && targetRole != null) {
			boolean shouldCheck = false;
			
			for (GrantedAuthority authority : authentication.getAuthorities()) {
				if (authority.getAuthority().equals(targetRole)) {
					shouldCheck = true;
					break;
				}
			}
			
			if (shouldCheck && allowedIPAddress.size() > 0) {
				boolean shouldAllow = false;
				for (String ipAddress : allowedIPAddress) {
					if (req.getRemoteAddr().equals(ipAddress)) {
						shouldAllow = true;
						break;
					}
				}
				
				if (!shouldAllow) {
					throw new AccessDeniedException("Access has bean for your IP address : " + req.getRemoteAddr());
				}
			}	
		} else {
			logger.warn("Thre IPRoleAuthenticationFilter should be placed....");
		}
		
		chain.doFilter(req, res);
		
		
	}

}
