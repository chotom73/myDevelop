package sample.spring.bankapp.dao;

import java.util.Date;

import org.slf4j.*;

import sample.spring.bankapp.domain.AccountStatement;

public class AccountStatementDaoImpl implements AccountStatementDao {
	private static Logger logger = LoggerFactory.getLogger(AccountStatementDaoImpl.class);
	
	@Override
	public AccountStatement getAccountStatement(Date from, Date to) {
		logger.info("Getting account statement");
		return new AccountStatement();
	}

}
