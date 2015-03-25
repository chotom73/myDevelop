package com.packtpub.springsecurity.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.packtpub.springsecurity.security.IChangePassword;
import com.packtpub.springsecurity.service.IUserService;

/**
 * Used to service account requests.
 * 
 * @author Mularien
 */
@Controller
public class AccountController extends BaseController {
	// Ch 5 Update to add service tier
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/account/home.do")
	public void accountHome() {		
	}
	@RequestMapping(value="/account/changePassword.do",method=RequestMethod.GET)
	public void showChangePasswordPage() {		
	}
	@RequestMapping(value="/account/changePassword.do",method=RequestMethod.POST)
	public String submitChangePasswordPage(@RequestParam("password") String newPassword) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = principal.toString();
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		}
		
		userService.changePassword(username, newPassword);
		SecurityContextHolder.clearContext();
		
		return "redirect:home.do";
	}
	
	@RequestMapping("/account/listActiveUsers.do")
	public void listActiveUsers(Model model) {
		Map<Object, Date> lastActivityDates = new HashMap<Object, Date>();
		
		for (Object principle : sessionRegistry.getAllPrincipals()) {
			for (SessionInformation session : sessionRegistry.getAllSessions(principle, false)) {
				if (lastActivityDates.get(principle) == null) {
					lastActivityDates.put(principle, session.getLastRequest());
				} else {
					Date preLastRequest = lastActivityDates.get(principle);
					if (session.getLastRequest().after(preLastRequest)) {
						lastActivityDates.put(principle, session.getLastRequest());
					}
				}
			}	
		}
		
		model.addAttribute("activeUsers", lastActivityDates);
	}
}
