package com.apress.prospringmvc.moneytransfer.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.apress.prospringmvc.moneytransfer.annotation.profile.StubTransactionRepository;
import com.apress.prospringmvc.moneytransfer.repository.AccountRepository;
import com.apress.prospringmvc.moneytransfer.repository.MapBasedAccountRepository;
import com.apress.prospringmvc.moneytransfer.repository.MapBasedTransactionRepository;
import com.apress.prospringmvc.moneytransfer.repository.MoneyTransferServiceImpl;
import com.apress.prospringmvc.moneytransfer.repository.TransactionRepository;
import com.apress.prospringmvc.moneytransfer.service.MoneyTransferService;

@Configuration
@ComponentScan(basePackages = {"com.apress.prospringmvc.moneytransfer.scanning", "com.apress.prospringmvc.moneytransfer.repository"})
public class ApplicationContextConfiguration {
	
	@Bean
	public AccountRepository accountRepository() {
		return new MapBasedAccountRepository();		
	}
	
	/*
	@Bean
	public TransactionRepository transactionRepository() {
		return new MapBasedTransactionRepository();
	}
	*/
	
	@Bean
	public MoneyTransferService moneyTransferService() {
		return new MoneyTransferServiceImpl();
	}
	
	@Configuration
	@Profile(value = "test")
	public static class TestContextConfiguration {
		@Bean
		public TransactionRepository transactionRepository() {
			return new StubTransactionRepository();
		}
	}
	
	@Configuration
	@Profile(value = "local")
	public static class LocalContextConfiguration {
		@Bean
		public TransactionRepository transactionRepository() {
			return new MapBasedTransactionRepository();
		}
	}
	
}
