package com.apress.prospringmvc.bookstore.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apress.prospringmvc.bookstore.domain.Account;
import com.apress.prospringmvc.bookstore.service.AccountService;
import com.apress.prospringmvc.bookstore.service.AuthenticationException;

@Controller
@RequestMapping(value ="/login")
public class LoginController {

	public static final String ACCOUNT_ATTRIBUTE = "account";
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String handleLogin(@RequestParam String username,
																		 @RequestParam String password,
																		 RedirectAttributes redirect,
																		 HttpServletRequest request, HttpSession session) throws AuthenticationException {
		try {
			//String username = request.getParameter("username");
			//String password = request.getParameter("password");

			
			Account account = accountService.login(username, password);
			session.setAttribute(ACCOUNT_ATTRIBUTE, account);
			return "redirect:/index.htm";
			
		} catch(AuthenticationException ex) {
			redirect.addFlashAttribute("exception", ex);
			return "redirect:/login";
		}
	}
}
