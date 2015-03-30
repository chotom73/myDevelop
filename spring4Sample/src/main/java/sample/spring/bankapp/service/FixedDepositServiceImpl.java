package sample.spring.bankapp.service;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

import sample.spring.bankapp.dao.FixedDepositDao;
import sample.spring.bankapp.domain.FixedDepositDetails;

@Service(value="fixedDepositService")
public class FixedDepositServiceImpl implements FixedDepositService {
	private static Logger logger = LoggerFactory.getLogger(FixedDepositServiceImpl.class);
	
	@Autowired
	private Validator validator;
	
	@Autowired
	@Qualifier(value="myFixedDepositDao")
	private FixedDepositDao myFixedDepositDao;
	
	@Override
	public void createFixedDeposit(FixedDepositDetails fdd) throws Exception {
		// -- create fixed deposit
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(fdd, "Errors");
		validator.validate(fdd, bindingResult);
		if(bindingResult.getErrorCount() > 0) {
			logger.error("Errors were found while validating FixedDepositDetails instance");
		} else {
			myFixedDepositDao.createFixedDeposit(fdd);
			logger.info("Created fixed deposit");
		}
	}
}
