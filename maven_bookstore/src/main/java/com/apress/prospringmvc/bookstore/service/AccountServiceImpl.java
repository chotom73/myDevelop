package com.apress.prospringmvc.bookstore.service;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apress.prospringmvc.bookstore.domain.Account;
import com.apress.prospringmvc.bookstore.repository.AccountRepository;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account save(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccount(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkPassword(Account account, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLastLogin(Account account) {
		// TODO Auto-generated method stub<dependency>
	}
	
	
	
	@Override
	public Account login(String username, String password) throws AuthenticationException {
		Account account = this.accountRepository.findByUsername(username);
		if (account != null) {
			String pwd = DigestUtils.shaHex(password + "{" + username + "}");
			if (!account.getPassword().equalsIgnoreCase(pwd)) {
				throw new AuthenticationException("Wrong username/password combination.", "invalid.password");
			}
		} else {
			throw new AuthenticationException("Wrong username/password combination.", "invalid.username");
		}

		return account;
	}

}
