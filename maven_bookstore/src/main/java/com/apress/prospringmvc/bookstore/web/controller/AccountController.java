package com.apress.prospringmvc.bookstore.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.apress.prospringmvc.bookstore.domain.Account;
import com.apress.prospringmvc.bookstore.repository.AccountRepository;
import com.apress.prospringmvc.bookstore.repository.OrderRepository;

@Controller
@RequestMapping("/customer/account")
@SessionAttributes(types = Account.class)
public class AccountController {
	
	@Autowired
	private OrderRepository orderRepository; 
	
	@Autowired
	private AccountRepository accountRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, HttpSession session) {
		Account account = (Account) session.getAttribute(LoginController.ACCOUNT_ATTRIBUTE);
		model.addAttribute(account);
		model.addAttribute("orders", this.orderRepository.findByAccount(account));
		return "customer/account";
	}
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public String update(@ModelAttribute Account account) {
		this.accountRepository.save(account);
		return "redirect:/customer/account";
	}
}
