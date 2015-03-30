package sample.spring.bankapp.dao;

import java.util.Date;

import sample.spring.bankapp.domain.AccountStatement;

public interface AccountStatementDao {
	public AccountStatement getAccountStatement(Date from, Date to);
}
