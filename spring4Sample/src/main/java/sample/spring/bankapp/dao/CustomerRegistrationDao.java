package sample.spring.bankapp.dao;

import sample.spring.bankapp.domain.CustomerRegistrationDetails;

public interface CustomerRegistrationDao {
	void registerCustomer(
			CustomerRegistrationDetails customerRegistrationDetails);
}
