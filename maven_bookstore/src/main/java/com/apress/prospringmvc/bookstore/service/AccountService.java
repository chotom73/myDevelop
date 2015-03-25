package com.apress.prospringmvc.bookstore.service;


import com.apress.prospringmvc.bookstore.domain.Account;


public interface AccountService {
	Account save(Account account);
	
	Account getAccount(String username);
	
	void checkPassword(Account account, String password);
	
	void updateLastLogin(Account account);
	
	Account login(String username, String password) throws AuthenticationException;
}
