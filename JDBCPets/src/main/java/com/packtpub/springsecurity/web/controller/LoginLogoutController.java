package com.packtpub.springsecurity.web.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller is used to provide functionality for the login page.
 * 
 * @author Mularien
 */
@Controller
public class LoginLogoutController extends BaseController{
	@RequestMapping(method=RequestMethod.GET,value="/login.do")
	public void home() {
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/accessDenied.do")
	public void accessDenied(Model model, HttpServletRequest request) {
		
		Enumeration em = request.getAttributeNames();
		
		while(em.hasMoreElements()) {
			String key = (String)em.nextElement();
			System.out.println("Key[" + key + "] : " + request.getAttribute(key));
		}
	
		AccessDeniedException ex = null;
		ex = (AccessDeniedException) request.getAttribute("SPRING_SECURITY_403_EXCEPTION");
	
		StringWriter sw = new StringWriter();
		model.addAttribute("errorDetails", ex.getMessage());
		ex.printStackTrace(new PrintWriter(sw));
		model.addAttribute("errorTrace", sw.toString());
	}
}
