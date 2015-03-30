package sample.spring.bankapp.service;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sample.spring.bankapp.dao.FixedDepositDao;
import sample.spring.bankapp.domain.FixedDepositDetails;

@Service(value="fixedDepositServiceJsr303")
public class FixedDepositServiceJsr303 implements FixedDepositService {
	
	private static final Logger logger = LoggerFactory.getLogger(FixedDepositServiceJsr303.class);

	@Autowired
	private Validator validator;
	
	@Autowired
	@Qualifier(value="myFixedDepositDao")
	private FixedDepositDao myFixedDepositDao;

	@Override
	public void createFixedDeposit(FixedDepositDetails fixedDepositDetails) throws Exception {
		// TODO Auto-generated method stub
		Set<ConstraintViolation<FixedDepositDetails>> violations = validator.validate(fixedDepositDetails);
		
		Iterator<ConstraintViolation<FixedDepositDetails>> itr = violations.iterator();
		
		if (itr.hasNext()) {
			logger.error("Errors were found while validating");
		} else {
			myFixedDepositDao.createFixedDeposit(fixedDepositDetails);
		}
	}
	
	
}
