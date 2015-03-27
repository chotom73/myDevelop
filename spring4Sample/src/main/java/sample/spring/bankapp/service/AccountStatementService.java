package sample.spring.bankapp.service;

import java.util.Date;

import sample.spring.bankapp.domain.AccountStatement;

public interface AccountStatementService {
	public AccountStatement getAccountStatement(Date from, Date to);
}
