package sample.spring.bankapp;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import sample.spring.bankapp.dao.AccountStatementDao;
import sample.spring.bankapp.dao.AccountStatementDaoImpl;
import sample.spring.bankapp.dao.CustomerRegistrationDao;
import sample.spring.bankapp.dao.CustomerRegistrationDaoImpl;
import sample.spring.bankapp.dao.FixedDepositDao;
import sample.spring.bankapp.dao.FixedDepositDaoImpl;
import sample.spring.bankapp.domain.CustomerRegistrationDetails;
import sample.spring.bankapp.service.AccountStatementService;
import sample.spring.bankapp.service.AccountStatementServiceImpl;
import sample.spring.bankapp.service.CustomerRegistrationService;
import sample.spring.bankapp.service.CustomerRegistrationServiceImpl;
import sample.spring.bankapp.service.FixedDepositService;
import sample.spring.bankapp.service.FixedDepositServiceImpl;

@Configuration
public class BankAppConfiguration {
	
	@Bean(name = "accountStatementService")
	public AccountStatementService accountStatementService() {
		return new AccountStatementServiceImpl();
	}

	@Bean(name = "accountStatementDao")
	public AccountStatementDao accountStatementDao() {
		return new AccountStatementDaoImpl();
	}

	@Bean(name = "customerRegistrationService")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public CustomerRegistrationService customerRegistrationService() {
		return new CustomerRegistrationServiceImpl();
	}

	@Bean(name = "customerRegistrationDao")
	public CustomerRegistrationDao customerRegistrationDao() {
		return new CustomerRegistrationDaoImpl();
	}

	@Bean(name = "customerRegistrationDetails")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public CustomerRegistrationDetails customerRegistrationDetails() {
		return new CustomerRegistrationDetails();
	}

	@Bean(name = "fixedDepositService")
	public FixedDepositService fixedDepositService() {
		return new FixedDepositServiceImpl();
	}

	@Bean(name = "myFixedDepositDao")
	public FixedDepositDao fixedDepositDao() {
		return new FixedDepositDaoImpl();
	}
	
	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean();
	}

}
