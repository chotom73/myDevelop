package sample.spring.bankapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sample.spring.bankapp.domain.FixedDepositDetails;
import sample.spring.bankapp.service.FixedDepositService;
import sample.spring.bankapp.service.FixedDepositServiceJsr303;

public class BankApp_Validate {
	private static final Logger logger = LoggerFactory.getLogger(BankApp_Validate.class);

	public static void main(String args[]) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:META-INF/spring/applicationContext.xml");
		logger.info("Validating FixedDepositDetails object using Spring Validation API");

		FixedDepositService fixedDepositService = (FixedDepositServiceJsr303) context.getBean("fixedDepositServiceJsr303");
		fixedDepositService.createFixedDeposit(new FixedDepositDetails(1, 0,
				12, "someemail@somedomain.com"));
		fixedDepositService.createFixedDeposit(new FixedDepositDetails(1, 1000,
				12, "someemail@somedomain.com"));

		logger.info("Validating FixedDepositDetails object using JSR 303's Validator");
		FixedDepositService fixedDepositServiceJsr303 = (FixedDepositService) context
				.getBean("fixedDepositServiceJsr303");
		fixedDepositServiceJsr303.createFixedDeposit(new FixedDepositDetails(1,
				0, 12, "someemail@somedomain.com"));
		fixedDepositServiceJsr303.createFixedDeposit(new FixedDepositDetails(1,
				1000, 12, "someemail@somedomain.com"));
	}
}
