package sample.spring.bankapp;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import sample.spring.bankapp.domain.FixedDepositDetails;
import sample.spring.bankapp.service.AccountStatementService;
import sample.spring.bankapp.service.CustomerRegistrationService;
import sample.spring.bankapp.service.FixedDepositService;
import ch.qos.logback.core.Context;

public class BankApp_Configuaration {
	private static final Logger logger = LoggerFactory.getLogger(BankApp_Configuaration.class);
	
	
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BankAppConfiguration.class);
		CustomerRegistrationService customerRegistrationService_1 = (CustomerRegistrationService)applicationContext.getBean("customerRegistrationService");
		
		customerRegistrationService_1.setAccountNumber("account_1");
		customerRegistrationService_1.setAddress("address_1");
		customerRegistrationService_1.setDebitCardNumber("debitCardNumber_1");
		customerRegistrationService_1.register();
		logger.info("----------> Done with accessing CustomerRegistrationService");
		
		logger.info("----------> Beginning with accessing FixedDepositService");
		FixedDepositService fixedDepositService = applicationContext.getBean(FixedDepositService.class);
		fixedDepositService.createFixedDeposit(new FixedDepositDetails(1, 1000,12, "someemail@somedomain.com"));
		logger.info("----------> Done with accessing FixedDepositService");

		logger.info("----------> Beginning with accessing AccountStatementService");
		try {
			AccountStatementService accountStatementService = applicationContext.getBean(AccountStatementService.class);
			accountStatementService.getAccountStatement(new Date(), new Date());
		} catch (Exception e) {
			logger.error("Exception : " + e.toString());
		}
		logger.info("----------> Done with accessing AccountStatementService");
	}

}
